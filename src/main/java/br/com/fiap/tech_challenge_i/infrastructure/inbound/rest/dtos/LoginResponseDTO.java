package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.dtos;

public record LoginResponseDTO(String token, String login, Long expiresIn) {
}