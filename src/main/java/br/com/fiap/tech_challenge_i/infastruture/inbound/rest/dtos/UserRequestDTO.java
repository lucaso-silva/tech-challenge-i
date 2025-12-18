package br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos;

import br.com.fiap.tech_challenge_i.application.domain.Client;
import br.com.fiap.tech_challenge_i.application.domain.RestaurantOwner;
import br.com.fiap.tech_challenge_i.application.domain.User;
import br.com.fiap.tech_challenge_i.application.domain.exceptions.BusinesException;

public record UserRequestDTO(
        String name,
        String email,
        String login,
        String password,
        UserTypeDTO userTypeDTO,
        AddressDTO address) {

    public User toDomain() {
        if (this.userTypeDTO.equals(UserTypeDTO.CLIENT)) {
            return Client.builder()
                    .name(this.name())
                    .email(this.email())
                    .login(this.login())
                    .password(this.password())
                    .address(this.address().toDomain())
                    .build();

        } else if (this.userTypeDTO.equals(UserTypeDTO.RESTAURANT_OWNER)) {
            return RestaurantOwner.builder()
                    .name(this.name())
                    .email(this.email())
                    .login(this.login())
                    .password(this.password())
                    .address(this.address().toDomain())
                    .build();
        } else {
            throw new BusinesException("Invalid user type '%s'".formatted(this.userTypeDTO().toString()));
        }
    }

}
