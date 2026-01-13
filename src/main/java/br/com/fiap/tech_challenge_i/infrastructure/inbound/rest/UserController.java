package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest;

import java.net.URI;
import java.util.List;

import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.ports.inbound.ForUserService;
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

    @PreAuthorize("isAuthenticated() and #login == authentication.name")
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

    @PreAuthorize("isAuthenticated() and (#id != null && @userService.isOwner(#id, authentication.name))")
    @PutMapping("/{id}")
    public ResponseEntity<UserDetailResponseDTO> updateUser(@PathVariable Long id,
                                                            @Valid @RequestBody UpdateUserRequestDTO requestDTO) {
        User updatedUser = forUserService.updateUser(id, requestDTO.toCommand());

        return ResponseEntity.ok(UserDetailResponseDTO.toDTO(updatedUser));
    }

    @PreAuthorize("isAuthenticated() and #login == authentication.name")
    @PutMapping("/password/{login}")
    public ResponseEntity<Void> changePassword(@PathVariable String login,
                                              @Valid @RequestBody ChangePasswordRequestDTO requestDTO) {

        forUserService.changePassword(login, requestDTO.password(), requestDTO.newPassword());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated() and (#id != null && @userService.isOwner(#id, authentication.name))")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        forUserService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
