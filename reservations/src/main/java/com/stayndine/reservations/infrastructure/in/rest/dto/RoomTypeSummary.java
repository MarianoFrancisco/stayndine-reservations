package com.stayndine.reservations.infrastructure.out.rest.hotels.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.UUID;

public record RoomTypeSummary(
        UUID id,
        Integer totalUnits
) {
}