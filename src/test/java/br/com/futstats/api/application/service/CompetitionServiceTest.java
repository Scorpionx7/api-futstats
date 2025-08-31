package br.com.futstats.api.application.service;

import br.com.futstats.api.application.dto.CompetitionStandingsResponse;
import br.com.futstats.api.domain.model.Competition;
import br.com.futstats.api.domain.model.Standing;
import br.com.futstats.api.domain.model.Team;
import br.com.futstats.api.domain.repository.CompetitionRepository;
import br.com.futstats.api.domain.repository.StandingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompetitionServiceTest {

    @InjectMocks
    private CompetitionService competitionService;

    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private StandingRepository standingRepository;

    @Test
    void getStandingsByCompetition_DeveRetornarClassificacao_QuandoCompeticaoExistir() {
        // Arrange
        var mockCompetition = new Competition();
        mockCompetition.setId(1L);
        mockCompetition.setName("Brasileirão Série A");
        mockCompetition.setCountry("Brazil");

        var mockTeam = new Team();
        mockTeam.setName("Time de Teste");
        mockTeam.setLogo("logo.png");

        var mockStanding = new Standing();
        mockStanding.setCompetition(mockCompetition);
        mockStanding.setTeam(mockTeam);
        mockStanding.setSeason(2023);
        mockStanding.setTeamRank(1);
        mockStanding.setPoints(99);
        mockStanding.setGoalsDiff(50);
        mockStanding.setMatchesPlayed(38);
        mockStanding.setWins(30);
        mockStanding.setDraws(5);
        mockStanding.setLosses(3);

        List<Standing> mockStandingsList = List.of(mockStanding);

        when(competitionRepository.findById(1L)).thenReturn(Optional.of(mockCompetition));
        when(standingRepository.findByCompetitionIdAndSeasonOrderByTeamRankAsc(1L, 2023))
                .thenReturn(mockStandingsList);

        // Act
        CompetitionStandingsResponse result = competitionService.getStandingsByCompetition(1L, 2023);

        // Assert
        assertNotNull(result);
        assertEquals("Brasileirão Série A", result.competitionName());
        assertEquals(2023, result.season());
        assertEquals(1, result.standings().size());

        var standingResult = result.standings().get(0);
        assertEquals(1, standingResult.rank());
        assertEquals(99, standingResult.points());
        assertEquals("Time de Teste", standingResult.team().name());
        assertEquals("logo.png", standingResult.team().logo());
    }
}