package br.com.futstats.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${api.football.key:${API_FOOTBALL_KEY}}")
    private String apiKey;

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("https://v3.football.api-sports.io")
                .defaultHeader("x-rapidapi-key", apiKey)
                .build();
    }
}
