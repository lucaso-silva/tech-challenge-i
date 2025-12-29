package br.com.fiap.tech_challenge_i.infastruture.inbound.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.fiap.tech_challenge_i.application.domain.exceptions.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex,
                ex.getMessage(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }

    //TODO: Adicionar BusinesException - status code 400

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleGeneralException(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex,
                "An unexpected error occurred",
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
