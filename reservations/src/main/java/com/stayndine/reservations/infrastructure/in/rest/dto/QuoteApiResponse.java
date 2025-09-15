package com.stayndine.reservations.infrastructure.out.rest.hotels.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;

public record QuoteApiResponse(
        BigDecimal nightlyPrice,
        String currency
) {
}