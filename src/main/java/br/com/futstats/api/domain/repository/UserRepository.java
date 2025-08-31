package br.com.futstats.api.domain.repository;

import br.com.futstats.api.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    // Metodo que o Spring Security usará para buscar um usuário pelo nome

    UserDetails findByUsername(String username);

}
