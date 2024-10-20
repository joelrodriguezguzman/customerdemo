package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class ApiDocConfig {

    @Bean   
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Technical Challenge REST API")
                        .description("REST API for demo")
                        .contact(new Contact()
                                .name("API Support")
                                .url("https://github.com/joelrodriguezguzman/customerdemo")  
                                .email("joelrodriguez@outlook.com"))
                        .version("v1.0.0"));
    }   
}
