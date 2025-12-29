package br.com.fiap.tech_challenge_i.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.domain.exceptions.BusinesException;
import br.com.fiap.tech_challenge_i.application.domain.exceptions.NotFoundException;
import br.com.fiap.tech_challenge_i.application.ports.inbound.ForUserService;
import br.com.fiap.tech_challenge_i.application.ports.outbound.repositories.UserRepository;

@Service
public class UserService implements ForUserService {
    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User user) {
        //TODO: Adicionar uma verificação se o login é unico e retornar um erro adequado
        Optional<User> byEmail = this.findByEmail(user.getEmail());
        byEmail.ifPresent(u -> {
            throw new BusinesException("Email '%s' already used".formatted(user.getEmail()));
        });
        return repository.create(user);
    }

    @Override
    public User update(Long id, User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<User> findByNameLike(String name) {
        if (name == null || name.isBlank()) {
            return repository.findAll();
        } else {
            List<User> usersByNameLike = repository.findAll()
                    .stream()
                     //TODO: mudar para comparar a partir do inico da string, filtro pela letra "A" e retorna todos os nomes iniciados por "A".
                    .filter(n -> n.getName().toLowerCase().contains(name.toLowerCase()))
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
}
