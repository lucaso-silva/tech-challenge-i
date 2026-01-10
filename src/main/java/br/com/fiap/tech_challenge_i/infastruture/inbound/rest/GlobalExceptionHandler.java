package br.com.fiap.tech_challenge_i.infastruture.inbound.rest;

import br.com.fiap.tech_challenge_i.application.domain.exceptions.BusinessException;
import br.com.fiap.tech_challenge_i.application.domain.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final NotFoundException ex, final WebRequest request) {
        return handleExceptionInternal(ex,
                ex.getMessage(),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(value = { BusinessException.class })
    protected ResponseEntity<Object> handleBusinessException(final BusinessException ex, final WebRequest request) {
        return handleExceptionInternal(ex,
                ex.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleGeneralException(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex,
                "An unexpected error occurred",
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request){

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
