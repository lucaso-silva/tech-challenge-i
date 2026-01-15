package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
    @NotBlank(message = "Login is required")
    String login,
    
    @NotBlank(message = "Password is required")
    String password
) {}