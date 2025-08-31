package br.com.futstats.api.domain.repository;

import br.com.futstats.api.domain.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    //Metodo para buscar um campeonato pelo ID da API Externa
    Optional<Competition> findByApiFootballId(Integer apiFootballId);
}
