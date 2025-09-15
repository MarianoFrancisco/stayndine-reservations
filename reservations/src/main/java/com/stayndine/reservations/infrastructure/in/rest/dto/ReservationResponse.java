package com.stayndine.reservations.infrastructure.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ReservationResponse(
        UUID id, String code, String status,
        UUID hotelId, UUID userId,
        LocalDate checkIn, LocalDate checkOut, int guests,
        String currency, BigDecimal totalAmount,
        List<Item> items
) {
    public record Item(UUID roomTypeId, int quantity, BigDecimal pricePerNight, int nights, BigDecimal subtotal) {
    }
}