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
                                .description("Tech Challenge Fase I - Arquitetura e Desenvolvimento Java")
                                .version("v0.0.1")
                );

    }

}
