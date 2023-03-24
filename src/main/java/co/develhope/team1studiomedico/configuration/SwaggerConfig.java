package co.develhope.team1studiomedico.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe di configurazione che istanzia il bean della classe OpenAPI per introdurre
 * e abilitare l'utilizzo dello Swagger come tool di testing e documentazione API
 */
@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI MOBSOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Studio Medico API")
                        .description("Progetto Spring Studio Medico")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringDoc Wiki Documentation")
                        .url("https://springdoc.org/v2"));
    }

}