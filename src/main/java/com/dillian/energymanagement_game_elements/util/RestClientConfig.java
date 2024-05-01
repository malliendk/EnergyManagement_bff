package com.dillian.energymanagement_game_elements.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient base_api_restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Bean
    public RestClient funds_popularity_restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8082")
                .build();
    }
}
