package br.com.futstats.api.domain.repository;

import br.com.futstats.api.domain.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    // Metodo para buscar um time pelo ID da API Externa
    Optional<Team> findByApiFootballId(Integer apiFootballId);
}
