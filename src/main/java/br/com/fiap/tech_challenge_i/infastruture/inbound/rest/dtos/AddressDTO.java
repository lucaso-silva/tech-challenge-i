package br.com.fiap.tech_challenge_i.infastruture.inbound.rest.dtos;

import br.com.fiap.tech_challenge_i.application.domain.Address;

public record AddressDTO(
        String street,
        Integer number,
        String neighborhood,
        String city,
        String state,
        String zipCode) {

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
