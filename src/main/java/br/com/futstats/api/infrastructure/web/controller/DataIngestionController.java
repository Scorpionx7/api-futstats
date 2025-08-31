package br.com.futstats.api.infrastructure.web.controller;


import br.com.futstats.api.application.service.StandingsIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ingestion")
@RequiredArgsConstructor
public class DataIngestionController {

    private final StandingsIngestionService standingsIngestionService;

    @PostMapping("/standings")
    public ResponseEntity<String> triggerStandingsIngestion(
            @RequestParam("leagueId") Integer leagueId,
            @RequestParam("season") Integer season) {

        // Chamamos o mesmo serviço que o agendador usaria
        standingsIngestionService.ingestStandings(leagueId, season);

        return ResponseEntity.ok("Ingestão de dados para a liga " + leagueId + " na temporada " + season + " iniciada com sucesso.");
    }
}
