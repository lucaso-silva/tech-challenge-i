package br.com.fiap.tech_challenge_i.application.domain.exceptions;

public class NotFoundException extends DomainException {

    public NotFoundException(String message) {
        super(message);
    }

}
