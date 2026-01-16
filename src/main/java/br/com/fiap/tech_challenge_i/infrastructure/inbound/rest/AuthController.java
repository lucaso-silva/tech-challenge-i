package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest;

import br.com.fiap.tech_challenge_i.application.ports.inbound.ForUserService;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.LoginRequestDTO;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.LoginResponseDTO;
import br.com.fiap.tech_challenge_i.infrastructure.security.JwtService;
import br.com.fiap.tech_challenge_i.application.domain.exceptions.BusinessException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final ForUserService forUserService;
    private final JwtService jwtService;

    public AuthController(ForUserService forUserService, JwtService jwtService) {
        this.forUserService = forUserService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO requestDTO) {

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
