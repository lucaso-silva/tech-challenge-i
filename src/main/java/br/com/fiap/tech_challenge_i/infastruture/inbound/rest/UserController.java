package br.com.fiap.tech_challenge_i.infastruture.inbound.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.ports.inbound.ForUserService;
import br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos.UserDetailResponseDTO;
import br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos.UserResponseDTO;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final ForUserService forUserService;

    public UserController(ForUserService forUserService) {
        this.forUserService = forUserService;
    }

    @GetMapping
    public List<UserResponseDTO> findAll(@RequestParam(name = "name", required = false) String name) {
        return forUserService.findByNameLike(name)
                .stream().map(UserResponseDTO::toDTO).toList();
    }

    @GetMapping("/{login}")
    public UserDetailResponseDTO fetchUserByLogin(@PathVariable("login") String login) {

        User userByLogin = forUserService.findByLogin(login);
        return UserDetailResponseDTO.toDTO(userByLogin);

    }

}
