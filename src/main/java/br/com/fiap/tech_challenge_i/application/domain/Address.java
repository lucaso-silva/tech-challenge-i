package br.com.fiap.tech_challenge_i.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
    private Long id;
    private String street;
    private Integer number;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;

    public void updateFrom(Address address) {
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.neighborhood = address.getNeighborhood();
        this.city = address.getCity();
        this.state = address.getState();
        this.zipCode = address.getZipCode();
    }
}
