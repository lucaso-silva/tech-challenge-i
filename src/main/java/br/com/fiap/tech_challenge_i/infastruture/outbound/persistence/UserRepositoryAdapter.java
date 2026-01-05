package br.com.fiap.tech_challenge_i.infastruture.outbound.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.ports.outbound.repositories.UserRepository;
import br.com.fiap.tech_challenge_i.infastruture.outbound.persistence.entities.UserJPAEntity;
import br.com.fiap.tech_challenge_i.infastruture.outbound.persistence.repositories.UserJPARepository;

@Service
public class UserRepositoryAdapter implements UserRepository {

    private final UserJPARepository userJPARepository;

    public UserRepositoryAdapter(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    @Override
    public User create(User user) {
        return userJPARepository
                .save(UserJPAEntity.of(user))
                .toDomain();
    }

    @Override
    public User update(User user) {
        UserJPAEntity userJPAEntity = UserJPAEntity.of(user);
        userJPAEntity.setId(user.getId());

        return userJPARepository
                .save(userJPAEntity)
                .toDomain();
    }

    @Override
    public void deleteById(Long id) {
        // TODO: Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public List<User> findAll() {
        return userJPARepository
                .findAll()
                .stream()
                .map(UserJPAEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJPARepository
                .findById(id)
                .map(UserJPAEntity::toDomain);
    }

    @Override
    public List<User> findByNameLike(String name) {
        return userJPARepository
                .findByNameLike(name)
                .stream()
                .map(UserJPAEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJPARepository
                .findByEmail(email)
                .map(UserJPAEntity::toDomain);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Optional<UserJPAEntity> byLogin = userJPARepository.findByLogin(login);
        return byLogin.map(UserJPAEntity::toDomain);
    }

}
