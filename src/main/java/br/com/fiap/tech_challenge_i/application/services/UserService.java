package br.com.fiap.tech_challenge_i.application.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.fiap.tech_challenge_i.application.usecase.UpdateUserCommand;
import br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos.UpdateUserRequestDTO;
import org.springframework.stereotype.Service;

import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.domain.exceptions.BusinesException;
import br.com.fiap.tech_challenge_i.application.domain.exceptions.NotFoundException;
import br.com.fiap.tech_challenge_i.application.ports.inbound.ForUserService;
import br.com.fiap.tech_challenge_i.application.ports.outbound.repositories.UserRepository;

import static java.util.stream.Collectors.toList;

@Service
public class UserService implements ForUserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        Optional<User> byEmail = this.findByEmail(user.getEmail());
        byEmail.ifPresent(u -> {
            throw new BusinesException("Email '%s' already used".formatted(user.getEmail()));
        });

        Optional<User> byLogin = this.getByLogin(user.getLogin());
        byLogin.ifPresent(u -> {
            throw new BusinesException("Login '%s' already used".formatted(user.getLogin()));
        });

        return repository.create(user);
    }

    @Override
    public User updateUser(Long id, UpdateUserCommand user) {
        User existentUser = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("User with id '%s' not found".formatted(id)));

        Optional<User> byEmail = repository.findByEmail(user.email());
        byEmail.ifPresent(u -> {
            if(!u.getId().equals(existentUser.getId())) {
                throw new BusinesException("Email '%s' already used".formatted(user.email()));
            }
        });

        user.applyTo(existentUser);
        existentUser.setLastModifiedDate(LocalDateTime.now());

        return repository.update(existentUser);
    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword) {

    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(()-> new NotFoundException("User with id '%s' not found".formatted(id)));
        repository.deleteById(id);
    }

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

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User findByLogin(String login) {

        Optional<User> byLogin = repository.findByLogin(login);
        return byLogin
                .orElseThrow(() -> new NotFoundException("No users with the login '%s' were found".formatted(login)));
    }

    @Override
    public boolean validateLogin(String login, String password) {
        return false;
    }

    private Optional<User> getByLogin(String login) {
        return repository.findByLogin(login);
    }
}
