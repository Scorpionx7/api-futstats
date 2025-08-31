package br.com.futstats.api.application.dto;

// Record para receber os dados de login (JSON)
public record AuthenticationDTO(
        String username,
        String password
) {
}
