package br.com.futstats.api.application.dto;

// DTO simples para representar um time na resposta da API
public record TeamResponse(
        String name,
        String logo
) {}
