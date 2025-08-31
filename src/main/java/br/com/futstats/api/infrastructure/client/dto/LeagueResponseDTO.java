package br.com.futstats.api.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// Representa o objeto "league" dentro da resposta
public record LeagueResponseDTO(@JsonProperty("league") LeagueDTO league) {
}
