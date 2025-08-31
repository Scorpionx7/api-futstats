package br.com.futstats.api.domain.repository;

import br.com.futstats.api.domain.model.Standing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StandingRepository extends JpaRepository<Standing, Long> {

    List<Standing> findByCompetitionIdAndSeason(Long competitionId, int season);

    @Query("SELECT s FROM Standing s JOIN FETCH s.team WHERE s.competition.id = :competitionId AND s.season = :season ORDER BY s.teamRank ASC")
    List<Standing> findByCompetitionIdAndSeasonOrderByTeamRankAsc(Long competitionId, int season);


}
