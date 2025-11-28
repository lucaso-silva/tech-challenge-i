package br.com.fiap.tech_challenge_i.infastruture.outbound.persistence.entities;

import java.time.LocalDate;

import br.com.fiap.tech_challenge_i.application.domain.Client;
import br.com.fiap.tech_challenge_i.application.domain.RestaurantOwner;
import br.com.fiap.tech_challenge_i.application.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserTypeJPAEntity userType;

    @NotNull
    @Column(nullable = false)
    private LocalDate lastModifiedDate;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressJPAEntity address;

    public static UserJPAEntity of(User user) {
        return new UserJPAEntity();
    }

    public User toDomain() {
        if (this.userType.equals(UserTypeJPAEntity.CLIENT)) {
            return Client.builder()
                    .id(this.getId())
                    .name(this.getName())
                    .email(this.getEmail())
                    .login(this.getLogin())
                    .password(this.getPassword())
                    .lastModifiedDate(this.getLastModifiedDate())
                    .address(this.getAddress().toDomain())
                    .build();

        }
        if (this.userType.equals(UserTypeJPAEntity.RESTAURANT_OWNER)) {
            return RestaurantOwner.builder()
                    .id(this.getId())
                    .name(this.getName())
                    .email(this.getEmail())
                    .login(this.getLogin())
                    .password(this.getPassword())
                    .lastModifiedDate(this.getLastModifiedDate())
                    .address(this.getAddress().toDomain())
                    .build();
        }
        throw new IllegalArgumentException("Unknown type");
    }

}
