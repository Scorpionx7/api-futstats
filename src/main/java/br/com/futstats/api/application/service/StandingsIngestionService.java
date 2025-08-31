package br.com.futstats.api.application.service;

import br.com.futstats.api.domain.model.Competition;
import br.com.futstats.api.domain.model.Standing;
import br.com.futstats.api.domain.model.Team;
import br.com.futstats.api.domain.repository.CompetitionRepository;
import br.com.futstats.api.domain.repository.StandingRepository;
import br.com.futstats.api.domain.repository.TeamRepository;
import br.com.futstats.api.infrastructure.client.dto.ApiResponseDTO;
import br.com.futstats.api.infrastructure.client.dto.LeagueDTO;
import br.com.futstats.api.infrastructure.client.dto.TeamDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StandingsIngestionService {

    private static final Logger logger = LoggerFactory.getLogger(StandingsIngestionService.class);

    private final WebClient webClient;
    private final CompetitionRepository competitionRepository;
    private final TeamRepository teamRepository;
    private final StandingRepository standingRepository;

    @Transactional
    public void ingestStandings(Integer leagueId, Integer season) {
        logger.info("Iniciando ingestão de dados para a liga {} na temporada {}", leagueId, season);

        ApiResponseDTO apiResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/standings")
                        .queryParam("league", leagueId)
                        .queryParam("season", season)
                        .build())
                .retrieve()
                .bodyToMono(ApiResponseDTO.class)
                .block(); // Usamos .block() pois é uma tarefa agendada (contexto não-reativo)

        if (apiResponse == null || apiResponse.response() == null || apiResponse.response().isEmpty()) {
            logger.warn("Nenhum dado retornado pela API para a liga {} na temporada {}", leagueId, season);
            return;
        }

        var leagueResponse = apiResponse.response().get(0).league();
        if (leagueResponse.standings() == null || leagueResponse.standings().isEmpty()) {
            logger.warn("Nenhuma classificação (standings) encontrada para a liga {}", leagueId);
            return;
        }

        Competition competition = upsertCompetition(leagueResponse);
        List<Standing> standingsToSave = new ArrayList<>();

        // A API retorna uma lista de listas de classificações (para ligas com grupos)
        // Para o Brasileirão, por exemplo, haverá apenas uma lista interna.
        leagueResponse.standings().get(0).forEach(standingDTO -> {
            Team team = upsertTeam(standingDTO.team());

            Standing standing = new Standing();
            standing.setCompetition(competition);
            standing.setTeam(team);
            standing.setSeason(season);
            standing.setTeamRank(standingDTO.rank());
            standing.setPoints(standingDTO.points());
            standing.setGoalsDiff(standingDTO.goalsDiff());
            standing.setMatchesPlayed(standingDTO.all().played());
            standing.setWins(standingDTO.all().wins());
            standing.setDraws(standingDTO.all().draws());
            standing.setLosses(standingDTO.all().losses());

            standingsToSave.add(standing);
        });

        // Antes de salvar os novos dados, podemos apagar os antigos para evitar duplicatas
        // (implementação mais robusta seria necessária aqui, mas para começar é suficiente)
        standingRepository.deleteAll(standingRepository.findByCompetitionIdAndSeason(competition.getId(), season));
        standingRepository.saveAll(standingsToSave);

        logger.info("Ingestão de dados para a liga {} finalizada. {} classificações salvas.", leagueId, standingsToSave.size());
    }

    private Competition upsertCompetition(LeagueDTO leagueDTO) {
        // Busca a competição pelo ID da API. Se não existir, cria uma nova.
        return competitionRepository.findByApiFootballId(leagueDTO.id().intValue())
                .orElseGet(() -> {
                    Competition newCompetition = new Competition();
                    newCompetition.setApiFootballId(leagueDTO.id().intValue());
                    newCompetition.setName(leagueDTO.name());
                    newCompetition.setCountry(leagueDTO.country());
                    newCompetition.setLogo(leagueDTO.logo());
                    return competitionRepository.save(newCompetition);
                });
    }

    private Team upsertTeam(TeamDTO teamDTO) {
        // Lógica similar para o time.
        return teamRepository.findByApiFootballId(teamDTO.id())
                .orElseGet(() -> {
                    Team newTeam = new Team();
                    newTeam.setApiFootballId(teamDTO.id());
                    newTeam.setName(teamDTO.name());
                    // A API não fornece país do time no endpoint de standings, pode ser preenchido por outro endpoint.
                    newTeam.setLogo(teamDTO.logo());
                    return teamRepository.save(newTeam);
                });
    }
}