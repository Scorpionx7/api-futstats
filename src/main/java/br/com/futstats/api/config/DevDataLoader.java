package br.com.futstats.api.config;

import br.com.futstats.api.domain.model.User;
import br.com.futstats.api.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev") // Esta classe só será executada quando o perfil "dev" estiver ativo
@RequiredArgsConstructor
public class DevDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se o usuário de teste já existe
        if (userRepository.findByUsername("testuser") == null) {
            String encodedPassword = passwordEncoder.encode("password123");
            User testUser = new User(null, "testuser", encodedPassword);
            userRepository.save(testUser);
            System.out.println(">>> Usuário de teste 'testuser' criado com a senha 'password123'");
        }
    }
}