package br.com.fiap.tech_challenge_i.application.usecase;

import br.com.fiap.tech_challenge_i.application.domain.Address;
import br.com.fiap.tech_challenge_i.application.domain.User;

public record UpdateUserCommand(
        String name,
        String email,
        Address address){

    public void applyTo(User user){
        user.setName(name);
        user.setEmail(email);

        if(address != null){
            user.getAddress().updateFrom(address);
        }
    }
}
