package br.com.futstats.api.infrastructure.web.controller;

import br.com.futstats.api.application.dto.CompetitionStandingsResponse;
import br.com.futstats.api.application.service.ICompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/competitions")
@RequiredArgsConstructor
public class CompetitionController {

    private final ICompetitionService competitionService;

    @GetMapping("/{id}/standings")
    public ResponseEntity<CompetitionStandingsResponse> getStandings(
            @PathVariable("id") Long competitionId,
            @RequestParam("season") int season) {

        var standings = competitionService.getStandingsByCompetition(competitionId, season);
        return ResponseEntity.ok(standings);
    }


}
