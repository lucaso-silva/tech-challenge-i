package br.com.fiap.tech_challenge_i.infrastructure.inbound.rest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI users() {
        return  new OpenAPI()
                .info(
                        new Info().title("Users API")
                                .description("API de gerenciamento de usuários (clientes e dono de restaurante), desenvolvida durante o Tech Challenge da Fase I da Pós-Graduação em Arquitetura e Desenvolvimento Java (FIAP)")
                                .version("v1.0.0")
                );

    }

}
