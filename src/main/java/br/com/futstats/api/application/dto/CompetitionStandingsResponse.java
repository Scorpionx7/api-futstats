package br.com.futstats.api.application.dto;

import java.util.List;

// O objeto completo que será retornado no nosso endpoint de classificação
public record CompetitionStandingsResponse(
        String competitionName,
        String country,
        Integer season,
        List<StandingResponse> standings
) {
}
