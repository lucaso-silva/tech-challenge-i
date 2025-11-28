package br.com.fiap.tech_challenge_i.application.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class User {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private LocalDate lastModifiedDate;
    private Address address;

}
