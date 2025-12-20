package br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos;

import br.com.fiap.tech_challenge_i.application.domain.Client;
import br.com.fiap.tech_challenge_i.application.domain.RestaurantOwner;
import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.domain.exceptions.BusinesException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
        @NotBlank(message = "The name cant be empty") String name,
        @NotBlank(message = "The email cant be empty") String email,
        @NotBlank(message = "The login cant be empty") String login,
        @NotBlank(message = "The password cant be empty") String password,
        UserTypeDTO userTypeDTO,
        @NotNull(message = "The address cant be empty") AddressDTO address) {

    public User toDomain() {
        if (this.userTypeDTO.equals(UserTypeDTO.CLIENT)) {
            return Client.builder().name(this.name()).email(this.email()).login(this.login())
                    .password(this.password()).address(this.address().toDomain()).build();

        } else if (this.userTypeDTO.equals(UserTypeDTO.RESTAURANT_OWNER)) {
            return RestaurantOwner.builder().name(this.name()).email(this.email())
                    .login(this.login()).password(this.password())
                    .address(this.address().toDomain()).build();
        } else {
            throw new BusinesException(
                    "Invalid user type '%s'".formatted(this.userTypeDTO().toString()));
        }
    }
}
