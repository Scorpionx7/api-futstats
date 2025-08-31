package br.com.futstats.api.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// Estatísticas de jogos (todos)
public record AllMatchesDTO(
        Integer played,
        @JsonProperty("win") Integer wins,
        @JsonProperty("draw") Integer draws,
        @JsonProperty("lose") Integer losses
) {}
