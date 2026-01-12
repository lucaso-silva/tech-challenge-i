package br.com.fiap.tech_challenge_i.infrastructure.outbound.persistence.entities;

import br.com.fiap.tech_challenge_i.application.domain.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class AddressJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipCode;

    public static AddressJPAEntity of(Address address) {
        return AddressJPAEntity.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

    public Address toDomain() {
        return Address.builder()
                .id(this.getId())
                .street(this.getStreet())
                .number(this.getNumber())
                .neighborhood(this.getNeighborhood())
                .city(this.getCity())
                .state(this.getState())
                .zipCode(this.getZipCode())
                .build();
    }
}
