package br.com.fiap.tech_challenge_i.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("client")
public class Client extends User {
}
