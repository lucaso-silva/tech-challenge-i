package br.com.fiap.tech_challenge_i.infastruture.outbound.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.tech_challenge_i.infastruture.outbound.persistence.entities.UserJPAEntity;

@Repository
public interface UserJPARepository extends JpaRepository<UserJPAEntity, Long> {

    List<UserJPAEntity> findByNameLike(String name);

    Optional<UserJPAEntity> findByLogin(String login);

    Optional<UserJPAEntity> findByEmail(String email);

}
