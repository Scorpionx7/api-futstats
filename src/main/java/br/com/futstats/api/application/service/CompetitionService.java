package br.com.futstats.api.application.service;

import br.com.futstats.api.application.dto.CompetitionStandingsResponse;
import br.com.futstats.api.application.dto.StandingResponse;
import br.com.futstats.api.application.dto.TeamResponse;
import br.com.futstats.api.domain.model.Standing;
import br.com.futstats.api.domain.repository.CompetitionRepository;
import br.com.futstats.api.domain.repository.StandingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetitionService implements ICompetitionService{

    private final CompetitionRepository competitionRepository;
    private final StandingRepository standingRepository;

    @Override
    @Cacheable(value = "standings", key = "#competitionId + '-' + #season")
    public CompetitionStandingsResponse getStandingsByCompetition(Long competitionId, int season) {
        var competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new EntityNotFoundException("Competição não encontrada com o ID: " + competitionId));

        List<Standing> standings = standingRepository.findByCompetitionIdAndSeasonOrderByTeamRankAsc(competitionId, season);

        var standingResponses = standings.stream()
                .map(this::toStandingResponse)
                .toList();

        return new CompetitionStandingsResponse(
                competition.getName(),
                competition.getCountry(),
                season,
                standingResponses);
    }

    private StandingResponse toStandingResponse(Standing standing) {
        var teamResponse = new TeamResponse(standing.getTeam().getName(), standing.getTeam().getLogo());
        return new StandingResponse(
                standing.getTeamRank(),
                teamResponse,
                standing.getPoints(),
                standing.getGoalsDiff(),
                standing.getMatchesPlayed(),
                standing.getWins(),
                standing.getDraws(),
                standing.getLosses()
        );
    }
}
