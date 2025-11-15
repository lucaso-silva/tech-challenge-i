package br.com.fiap.tech_challenge_i.services;

import br.com.fiap.tech_challenge_i.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Long id);
    List<User> findAll(int size, int offset);
    User create(User user);
    User update(Long id, User user);
    void delete(Long id);
}
