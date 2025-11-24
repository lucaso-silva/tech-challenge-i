package br.com.fiap.tech_challenge_i.application.ports.outbound.repositories;

import br.com.fiap.tech_challenge_i.application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameLike(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findByLogin(String login);
}
