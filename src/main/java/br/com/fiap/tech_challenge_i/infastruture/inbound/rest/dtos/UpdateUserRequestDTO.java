package br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos;

import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.usecase.UpdateUserCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRequestDTO(@NotBlank(message = "The name can't be empty") String name,
                                   @NotBlank(message = "The email can't be empty") String email,
                                   @NotNull(message = "The address can't be empty") AddressDTO address) {

    public UpdateUserCommand toCommand() {
        return new UpdateUserCommand(
                name,
                email,
                address.toDomain()
        );
    }
}

