package br.com.futstats.api.infrastructure.web.controller;

import br.com.futstats.api.AbstractIntegrationTest;
import br.com.futstats.api.domain.model.Competition;
import br.com.futstats.api.domain.model.Standing;
import br.com.futstats.api.domain.model.Team;
import br.com.futstats.api.domain.repository.CompetitionRepository;
import br.com.futstats.api.domain.repository.StandingRepository;
import br.com.futstats.api.domain.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CompetitionControllerIT extends AbstractIntegrationTest {

    // Injetamos o MockMvc, que será autoconfigurado
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private StandingRepository standingRepository;

    private Competition competition;

    @BeforeEach
    void setUp() {
        // Limpa e prepara o banco de dados antes de cada teste
        standingRepository.deleteAll();
        teamRepository.deleteAll();
        competitionRepository.deleteAll();

        // Arrange: Inserimos dados diretamente no banco de teste
        this.competition = competitionRepository.save(new Competition(null, 71, "Brasileirão", "Brazil", "logo.png"));
        Team team = teamRepository.save(new Team(null, 127, "Palmeiras", "Brazil", "logo.png"));
        Standing standing = new Standing();
        standing.setCompetition(competition);
        standing.setTeam(team);
        standing.setSeason(2023);
        standing.setTeamRank(1);
        standing.setPoints(70);
        standing.setGoalsDiff(31);
        standingRepository.save(standing);
    }

    @Test
    @WithMockUser
    void getStandings_DeveRetornar200_QuandoBuscarClassificacao() throws Exception {
        // Act & Assert com MockMvc
        mockMvc.perform(get("/api/v1/competitions/{id}/standings", competition.getId())
                        .param("season", "2023")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.competitionName").value("Brasileirão"))
                .andExpect(jsonPath("$.standings.length()").value(1))
                .andExpect(jsonPath("$.standings[0].team.name").value("Palmeiras"))
                .andExpect(jsonPath("$.standings[0].points").value(70));
    }
}