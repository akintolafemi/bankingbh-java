/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankingbh.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Makintola
 */

@Configuration
public class SwaggerConfig {
     @Bean
    public OpenAPI customOpenAPI() {
        // Add security scheme for Bearer Token
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer") //specify type of authentication
                .bearerFormat("JWT") //expected token format is Json Web Token
                .name("Authorization"); //header key

        // Add security requirement to apply globally
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Authorization");

        return new OpenAPI()
                .info(new Info()
                        .title("BankingBH Accounts API")
                        .version("1.0")
                        .description("API to be used for opening a new 'current account' of already existing customers."))
                .addSecurityItem(securityRequirement)
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("Authorization", securityScheme))
                        .addSecurityItem(new SecurityRequirement().addList("ngrok-skip-browser-warning", "1"))
                .servers(List.of(new Server().url("https://afd1-203-161-56-189.ngrok-free.app/")));
    }
}
