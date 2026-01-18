package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.controller;

import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.ports.inbound.ForUserService;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.api.UserApi;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
public class UserController implements UserApi {

    private final ForUserService forUserService;

    public UserController(ForUserService forUserService) {
        this.forUserService = forUserService;
    }

    @Override
    public ResponseEntity<List<UserResponseDTO>> findAll(String name) {
        return ResponseEntity.ok(
                forUserService.findByNameLike(name)
                        .stream().map(UserResponseDTO::toDTO).toList());
    }

    @PreAuthorize("isAuthenticated() and #login == authentication.principal")
    @Override
    public ResponseEntity<UserDetailResponseDTO> fetchUserByLogin(String login) {

        User userByLogin = forUserService.findByLogin(login);
        return ResponseEntity.ok(UserDetailResponseDTO.toDTO(userByLogin));
    }

    @Override
    public ResponseEntity<UserResponseDTO> createUser(UserRequestDTO requestDTO) {

        User user = forUserService.create(requestDTO.toDomain());
        URI uri = URI.create("/v1/user/" + user.getLogin());
        return ResponseEntity.created(uri).body(UserResponseDTO.toDTO(user));

    }

    @PreAuthorize("isAuthenticated() and #login == authentication.principal")
    @Override
    public ResponseEntity<UserDetailResponseDTO> updateUser(String login, UpdateUserRequestDTO requestDTO) {

        User updatedUser = forUserService.updateUser(login, requestDTO.toCommand());
        return ResponseEntity.ok(UserDetailResponseDTO.toDTO(updatedUser));
    }

    @PreAuthorize("isAuthenticated() and #login == authentication.principal")
    @Override
    public ResponseEntity<Void> changePassword(String login,  ChangePasswordRequestDTO requestDTO) {

        forUserService.changePassword(login, requestDTO.password(), requestDTO.newPassword());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated() and #login == authentication.principal")
    @Override
    public ResponseEntity<Void> deleteUser(String login) {

        forUserService.delete(login);
        return ResponseEntity.noContent().build();
    }
}
