package br.com.fiap.tech_challenge_i.application.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.fiap.tech_challenge_i.application.usecase.UpdateUserCommand;
import org.springframework.stereotype.Service;

import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.domain.exceptions.BusinessException;
import br.com.fiap.tech_challenge_i.application.domain.exceptions.NotFoundException;
import br.com.fiap.tech_challenge_i.application.ports.inbound.ForUserService;
import br.com.fiap.tech_challenge_i.application.ports.outbound.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements ForUserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public User create(User user) {
        Optional<User> byEmail = this.findByEmail(user.getEmail());
        byEmail.ifPresent(u -> {
            throw new BusinessException("Email '%s' already used".formatted(user.getEmail()));
        });

        Optional<User> byLogin = this.getByLogin(user.getLogin());
        byLogin.ifPresent(u -> {
            throw new BusinessException("Login '%s' already used".formatted(user.getLogin()));
        });

        return repository.create(user);
    }

    @Transactional
    @Override
    public User updateUser(Long id, UpdateUserCommand updateUser) {
        User existentUser = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("User with id '%s' not found".formatted(id)));

        Optional<User> byEmail = repository.findByEmail(updateUser.email());
        byEmail.ifPresent(u -> {
            if(!u.getId().equals(existentUser.getId())) {
                throw new BusinessException("Email '%s' already used".formatted(updateUser.email()));
            }
        });

        updateUser.applyTo(existentUser);
        existentUser.setLastModifiedDate(LocalDateTime.now());

        return repository.update(existentUser);
    }

    @Transactional
    @Override
    public void changePassword(String login, String oldPassword, String newPassword) {
        User existentUser = findByLogin(login);

        if(!existentUser.getPassword().equals(oldPassword)) {
            throw new BusinessException("The current password provided is incorrect");
        }

        if(oldPassword.equals(newPassword)) {
            throw new BusinessException("The new password cannot be the same as the current password");
        }

        existentUser.setPassword(newPassword);
        existentUser.setLastModifiedDate(LocalDateTime.now());

        repository.update(existentUser);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(()-> new NotFoundException("User with id '%s' not found".formatted(id)));
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findByNameLike(String name) {
        if (name == null || name.isBlank()) {
            return repository.findAll();
        } else {
            List<User> usersByNameLike = repository.findAll()
                    .stream()
                    .filter( u ->
                        Arrays.stream(u.getName().toLowerCase().split("\\s+"))
                                .anyMatch(word -> word.startsWith(name.toLowerCase()))
                    )
                    .toList();
            if (usersByNameLike.isEmpty()) {
                throw new NotFoundException("No user was found that matches the search term '%s'.".formatted(name));
            }

            return usersByNameLike;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByLogin(String login) {

        Optional<User> byLogin = repository.findByLogin(login);
        return byLogin
                .orElseThrow(() -> new NotFoundException("No users with the login '%s' were found".formatted(login)));
    }

    @Transactional(readOnly = true)
    @Override
    public boolean validateLogin(String login, String password) {
        return this.getByLogin(login)
                .map(u -> u.getPassword().equals(password))
                .isPresent();
    }

    private Optional<User> getByLogin(String login) {
        return repository.findByLogin(login);
    }
}
