package br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos;

import br.com.fiap.tech_challenge_i.application.domain.Address;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(
        @NotBlank(message = "The street can't be empty") String street,
        @NotNull(message = "The number can't be empty") @Min(1) Integer number,
        @NotBlank(message = "The neighborhood can't be empty") String neighborhood,
        @NotBlank(message = "The city can't be empty") String city,
        @NotBlank(message = "The state can't be empty") String state,
        @NotBlank(message = "The zipCode can't be empty") String zipCode) {

    public static AddressDTO toDTO(Address address) {
        return new AddressDTO(
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getZipCode());

    }

    public Address toDomain() {
        return Address.builder()
                .street(this.street())
                .number(this.number())
                .neighborhood(this.neighborhood())
                .city(this.city())
                .state(this.state())
                .zipCode(this.zipCode())
                .build();
    }
}
