package com.dillian.energymanagement_game_elements;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@AllArgsConstructor
public class EnergyManagementGameElementsApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(EnergyManagementGameElementsApplication.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE");
            }
        };
    }
}
