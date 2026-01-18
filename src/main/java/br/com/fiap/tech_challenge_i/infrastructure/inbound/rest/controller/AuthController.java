package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.controller;

import br.com.fiap.tech_challenge_i.application.domain.exceptions.BusinessException;
import br.com.fiap.tech_challenge_i.application.ports.inbound.ForUserService;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.api.AuthApi;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.LoginRequestDTO;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.LoginResponseDTO;
import br.com.fiap.tech_challenge_i.infrastructure.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    private final ForUserService forUserService;
    private final JwtService jwtService;

    public AuthController(ForUserService forUserService, JwtService jwtService) {
        this.forUserService = forUserService;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO requestDTO) {

        boolean isValid = forUserService.validateLogin(requestDTO.login(), requestDTO.password());

        if (!isValid) {
            throw new BusinessException("Invalid login or password");
        }

        String token = jwtService.generateToken(requestDTO.login());

        LoginResponseDTO response = new LoginResponseDTO(
                token,
                "Bearer",
                jwtService.getExpiration());

        return ResponseEntity.ok(response);
    }
}
