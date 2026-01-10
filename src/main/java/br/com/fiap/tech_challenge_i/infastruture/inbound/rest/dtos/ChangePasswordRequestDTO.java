package br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequestDTO(@NotBlank(message = "The password can't be empty") String password,
                                       @NotBlank(message = "The new password must be informed") String newPassword){

}
