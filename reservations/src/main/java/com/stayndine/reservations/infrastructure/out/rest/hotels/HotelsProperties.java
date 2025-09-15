package com.stayndine.reservations.infrastructure.out.rest.hotels;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hotels")
public record HotelsProperties(String baseUrl, String apiKey) {
}