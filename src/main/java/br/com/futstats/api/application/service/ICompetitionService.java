package br.com.futstats.api.application.service;

import br.com.futstats.api.application.dto.CompetitionStandingsResponse;

public interface ICompetitionService {

    CompetitionStandingsResponse getStandingsByCompetition(Long competitionId, int season);
}
