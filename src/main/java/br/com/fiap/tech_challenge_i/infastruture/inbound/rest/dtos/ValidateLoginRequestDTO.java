package br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos;

import jakarta.validation.constraints.NotBlank;

public record ValidateLoginRequestDTO(@NotBlank(message = "The user's login can't be empty") String login,
                                      @NotBlank(message = "Password can't be empty") String password) {
}
