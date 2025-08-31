package br.com.futstats.api.infrastructure.client.dto;

// Detalhes do time dentro da classificação
public record TeamDTO(
        Integer id,
        String name,
        String logo
) {
}
