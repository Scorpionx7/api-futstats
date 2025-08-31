package br.com.futstats.api.infrastructure.client.dto;

import java.util.List;

// Detalhes da Liga/Campeonato
public record LeagueDTO(
        Long id,
        String name,
        String country,
        String logo,
        Integer season,
        List<List<StandingDTO>> standings
) {}