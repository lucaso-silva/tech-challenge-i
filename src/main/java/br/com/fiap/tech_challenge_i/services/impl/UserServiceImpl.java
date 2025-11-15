package br.com.fiap.tech_challenge_i.services.impl;

import br.com.fiap.tech_challenge_i.entities.User;
import br.com.fiap.tech_challenge_i.repositories.UserRepository;
import br.com.fiap.tech_challenge_i.services.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll(int size, int offset) {
        return List.of();
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User update(Long id, User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
