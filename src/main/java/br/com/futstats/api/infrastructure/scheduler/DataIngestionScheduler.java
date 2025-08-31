package br.com.futstats.api.infrastructure.scheduler;


import br.com.futstats.api.application.service.StandingsIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataIngestionScheduler {

    private final StandingsIngestionService standingsIngestionService;

    // Cron: segundo, minuto, hora, dia do mês, mês, dia da semana
    // "0 0 5 * * ?" -> Executa todo dia às 5 da manhã.
    @Scheduled(cron = "0 0 5 * * ?")
    public void scheduleStandingsIngestion() {
        int currentYear = LocalDate.now().getYear();
        // ID 71 = Brasileirão Série A
        standingsIngestionService.ingestStandings(71, 2023);

        // ID 39 = Premier League (Inglaterra)
        // standingsIngestionService.ingestStandings(39, currentYear);
    }
}
