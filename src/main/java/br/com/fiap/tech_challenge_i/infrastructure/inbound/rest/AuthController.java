package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest;

import br.com.fiap.tech_challenge_i.application.ports.inbound.ForUserService;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.ValidateLoginRequestDTO;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.ValidateLoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final ForUserService forUserService;

    public AuthController(ForUserService forUserService) {
        this.forUserService = forUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<ValidateLoginResponseDTO> validateLogin(
            @Valid @RequestBody ValidateLoginRequestDTO requestDTO) {
        boolean isValid = forUserService.validateLogin(requestDTO.login(), requestDTO.password());
        return ResponseEntity.ok(new ValidateLoginResponseDTO(isValid));
    }
}