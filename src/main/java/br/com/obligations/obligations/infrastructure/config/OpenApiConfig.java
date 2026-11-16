package br.com.obligations.obligations.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${api.version}") String apiVersion) {

        return new OpenAPI()
                .info(new Info()
                        .title("Company Obligations API")
                        .description("""
                                API para gerenciamento de obrigações contábeis.
                                """)
                        .version(apiVersion));
    }
}
