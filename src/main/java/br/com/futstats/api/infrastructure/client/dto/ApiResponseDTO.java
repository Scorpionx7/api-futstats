package br.com.futstats.api.infrastructure.client.dto;

import java.util.List;

// Estrutura principal da resposta da API
public record ApiResponseDTO(List<LeagueResponseDTO> response) {}


