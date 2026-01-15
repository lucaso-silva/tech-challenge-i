package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.ports.inbound.ForUserService;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.ChangePasswordRequestDTO;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.UpdateUserRequestDTO;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.UserDetailResponseDTO;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.UserRequestDTO;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.UserResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final ForUserService forUserService;

    public UserController(ForUserService forUserService) {
        this.forUserService = forUserService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(@RequestParam(name = "name", required = false) String name) {
        return ResponseEntity.ok(
                forUserService.findByNameLike(name)
                        .stream().map(UserResponseDTO::toDTO).toList());
    }

    @PreAuthorize("isAuthenticated() and #login == authentication.principal")
    @GetMapping("/{login}")
    public ResponseEntity<UserDetailResponseDTO> fetchUserByLogin(@PathVariable("login") String login) {

        User userByLogin = forUserService.findByLogin(login);
        return ResponseEntity.ok(UserDetailResponseDTO.toDTO(userByLogin));

    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {

        User user = forUserService.create(requestDTO.toDomain());
        URI uri = URI.create("/v1/user/" + user.getLogin());
        return ResponseEntity.created(uri).body(UserResponseDTO.toDTO(user));

    }

    @PreAuthorize("isAuthenticated() and #login == authentication.principal")
    @PutMapping("/{login}")
    public ResponseEntity<UserDetailResponseDTO> updateUser(@PathVariable String login,
            @Valid @RequestBody UpdateUserRequestDTO requestDTO) {

        User updatedUser = forUserService.updateUser(login, requestDTO.toCommand());
        return ResponseEntity.ok(UserDetailResponseDTO.toDTO(updatedUser));
    }

    @PreAuthorize("isAuthenticated() and #login == authentication.principal")
    @PutMapping("/password/{login}")
    public ResponseEntity<Void> changePassword(@PathVariable String login,
            @Valid @RequestBody ChangePasswordRequestDTO requestDTO) {

        forUserService.changePassword(login, requestDTO.password(), requestDTO.newPassword());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated() and #login == authentication.principal")
    @DeleteMapping("/{login}")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {

        forUserService.delete(login);
        return ResponseEntity.noContent().build();
    }

}
