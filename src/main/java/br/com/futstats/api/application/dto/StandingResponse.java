package br.com.futstats.api.application.dto;

// Representa uma linha da tabela de classificação
public record StandingResponse(
        Integer rank,
        TeamResponse team,
        Integer points,
        Integer goalsDiff,
        Integer matchesPlayed,
        Integer wins,
        Integer draws,
        Integer losses
) {
}
