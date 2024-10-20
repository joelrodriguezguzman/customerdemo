package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.lang.NonNull;

@Configuration
public class WebConfig {

    /**
     * This bean is responsible for enabling CORS in the web application configuration.
     * <p>
     * CORS is a security feature implemented in web browsers that prevents web pages from accessing the app
     * </p>
     *
     * @param registry the {@link CorsRegistry} to configure CORS mappings
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            
            @Override
            public void addCorsMappings(@NonNull final CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}