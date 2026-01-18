package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.api;

import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.LoginRequestDTO;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.LoginResponseDTO;
import br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos.ValidateLoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth",
        description = "Endpoints responsáveis pela autenticação de usuários e geração de tokens JWT")
@RequestMapping("/v1/auth")
public interface AuthApi {

    @Operation(summary = "Autenticar usuário",
            description = "Valida as credenciais do usuário (login e senha) e, em caso de sucesso, retorna um token JWT",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciais do usuário (login e senha)", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token JWT gerado com sucesso após validação das credenciais", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas (login ou senha incorretos)", content = @Content)
    })
    @PostMapping
    ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO requestDTO);
}
