package com.zy7y.watch_server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Watch API")
                        .description("SpringDoc API 演示")
                        .version("v1.0.0")
                        )
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot + Mybatis Watch 后端")
                        .url("https://github.com/zy7y"))
                .components(new Components()
                        .addSecuritySchemes("token", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }
}