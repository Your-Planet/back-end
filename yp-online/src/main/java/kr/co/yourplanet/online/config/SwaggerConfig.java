package kr.co.yourplanet.online.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springDocOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .externalDocs(new ExternalDocumentation()
                        .description("YourPlanet Backend Github")
                        .url("https://github.com/Your-Planet/back-end"))
                .addServersItem(new Server().url("/").description("host"))
                .components(new Components()
                        .addSecuritySchemes("xAuthToken", bearerAuthScheme()))
                .addSecurityItem(new SecurityRequirement().addList("xAuthToken"));
    }

    private Info apiInfo() {
        return new Info()
                .title("Your Planet API")
                .description("Your Planet API 명세서입니다.")
                .version("v1.0.0");
    }

    private SecurityScheme bearerAuthScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-AUTH-TOKEN")
                .bearerFormat("JWT")
                .description("`Bearer ` 접두사 필수");
    }
}
