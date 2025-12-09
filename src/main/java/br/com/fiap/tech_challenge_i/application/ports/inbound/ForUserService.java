package br.com.fiap.tech_challenge_i.application.ports.inbound;

import java.util.List;
import java.util.Optional;

import br.com.fiap.tech_challenge_i.application.domain.User;

public interface ForUserService {

    User create(User user);

    User update(Long id, User user);

    void delete(Long id);

    List<User> findByNameLike(String name);

    Optional<User> findByEmail(String email);

    User findByLogin(String login);

}
