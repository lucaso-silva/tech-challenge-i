package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos;

import br.com.fiap.tech_challenge_i.application.domain.User;

public record UserResponseDTO(
        String name,
        String login) {

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(user.getName(), user.getLogin());
    }
}
