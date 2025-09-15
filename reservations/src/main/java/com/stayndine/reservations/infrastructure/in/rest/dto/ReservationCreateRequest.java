package com.stayndine.reservations.infrastructure.in.rest.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record ReservationCreateRequest(
        @NotNull UUID hotelId,
        @NotNull UUID roomTypeId,
        @Positive int quantity,
        @NotNull LocalDate checkIn,
        @NotNull LocalDate checkOut,
        @Positive int guests,
        @NotBlank String currency
) {
}