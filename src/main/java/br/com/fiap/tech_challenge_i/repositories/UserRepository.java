package br.com.fiap.tech_challenge_i.repositories;

import br.com.fiap.tech_challenge_i.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameLike(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findByLogin(String login);
}
