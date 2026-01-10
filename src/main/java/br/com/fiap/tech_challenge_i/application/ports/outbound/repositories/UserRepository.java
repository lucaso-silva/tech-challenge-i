package br.com.fiap.tech_challenge_i.application.ports.outbound.repositories;

import br.com.fiap.tech_challenge_i.application.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User create(User user);

    User update(User user);

    void deleteById(Long id);

    List<User> findAll();

    Optional<User> findById(Long id);

    List<User> findByNameLike(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findByLogin(String login);
}
