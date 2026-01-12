package br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos;

import br.com.fiap.tech_challenge_i.application.domain.Client;
import br.com.fiap.tech_challenge_i.application.domain.RestaurantOwner;
import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.domain.exceptions.BusinessException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
        @NotBlank(message = "The name can't be empty") String name,
        @NotBlank(message = "The email can't be empty") String email,
        @NotBlank(message = "The login can't be empty") String login,
        @NotBlank(message = "The password can't be empty") String password,
        UserTypeDTO userTypeDTO,
        @NotNull(message = "The address can't be empty") @Valid AddressDTO address) {

    public User toDomain() {
        if (this.userTypeDTO.equals(UserTypeDTO.CLIENT)) {
            return Client.builder().name(this.name()).email(this.email()).login(this.login())
                    .password(this.password()).address(this.address().toDomain()).build();

        } else if (this.userTypeDTO.equals(UserTypeDTO.RESTAURANT_OWNER)) {
            return RestaurantOwner.builder().name(this.name()).email(this.email())
                    .login(this.login()).password(this.password())
                    .address(this.address().toDomain()).build();
        } else {
            throw new BusinessException(
                    "Invalid user type '%s'".formatted(this.userTypeDTO().toString()));
        }
    }
}
