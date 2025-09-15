package com.stayndine.reservations.infrastructure.config;

import com.stayndine.reservations.infrastructure.out.rest.hotels.HotelsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(HotelsProperties.class)
public class RestClientsConfig {
    @Bean
    RestClient hotelsRestClient(HotelsProperties props) {
        return RestClient.builder()
                .baseUrl(props.baseUrl())
                .defaultHeader("X-Internal-Api-Key", props.apiKey())
                .build();
    }
}