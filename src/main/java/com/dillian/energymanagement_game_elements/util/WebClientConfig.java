package com.dillian.energymanagement_game_elements.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient base_api_restClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

}
