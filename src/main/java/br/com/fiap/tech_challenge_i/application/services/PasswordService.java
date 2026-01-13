package br.com.fiap.tech_challenge_i.application.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    public PasswordService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        // Handle migration case - plain text passwords
        if (requiresReset(encodedPassword)) {
            return rawPassword.equals(encodedPassword.substring("RESET_REQUIRED:".length()));
        }
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public boolean requiresReset(String encodedPassword) {
        return encodedPassword.startsWith("RESET_REQUIRED:");
    }
}