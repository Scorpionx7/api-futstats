package br.com.futstats.api.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// Detalhes da classificação de um time
public record StandingDTO(
        Integer rank,
        @JsonProperty("team") TeamDTO team,
        Integer points,
        Integer goalsDiff,
        @JsonProperty("all") AllMatchesDTO all
) {
}
