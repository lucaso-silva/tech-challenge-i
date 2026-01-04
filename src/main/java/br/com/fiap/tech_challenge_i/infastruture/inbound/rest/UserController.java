package br.com.fiap.tech_challenge_i.infastruture.inbound.rest;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.ports.inbound.ForUserService;
import br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos.UserDetailResponseDTO;
import br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos.UserRequestDTO;
import br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos.UserResponseDTO;
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

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailResponseDTO> updateUser(@PathVariable Long id,
                                                            @Valid @RequestBody UserRequestDTO requestDTO) {
        User updatedUser = forUserService.updateUser(id, requestDTO.toDomain());

        return ResponseEntity.ok(UserDetailResponseDTO.toDTO(updatedUser));
    }

    /*TODO:
    Rotas para:
    Update da senha /v1/user/password/{login} - login é unico

*/
}
