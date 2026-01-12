package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos;

import br.com.fiap.tech_challenge_i.application.domain.User;

public record UserDetailResponseDTO(
        String name,
        String email,
        String login,
        AddressDTO addressDTO) {

    public static UserDetailResponseDTO toDTO(User user) {
        return new UserDetailResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                AddressDTO.toDTO(user.getAddress()));
    }
}
