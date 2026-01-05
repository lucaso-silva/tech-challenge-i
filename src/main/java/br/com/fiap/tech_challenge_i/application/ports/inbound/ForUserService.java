package br.com.fiap.tech_challenge_i.application.ports.inbound;

import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.usecase.UpdateUserCommand;

import java.util.List;
import java.util.Optional;

public interface ForUserService {

    User create(User user);

    User updateUser(Long id, UpdateUserCommand user);

    void changePassword(Long id, String oldPassword, String newPassword);

    void delete(Long id);

    List<User> findByNameLike(String name);

    boolean validateLogin(String login, String password);

    Optional<User> findByEmail(String email);

    User findByLogin(String login);
}
