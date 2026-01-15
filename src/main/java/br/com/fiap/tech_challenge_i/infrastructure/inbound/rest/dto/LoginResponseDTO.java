package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dto;

public record LoginResponseDTO(
        String token,
        String type,
        Long expiresIn) {

}
