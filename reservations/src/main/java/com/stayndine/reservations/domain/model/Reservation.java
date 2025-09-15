package com.stayndine.reservations.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record Reservation(
        UUID id,
        UUID hotelId,
        UUID userId,
        UUID customerId,
        String code,
        Status status,
        LocalDate checkIn,
        LocalDate checkOut,
        int guests,
        String currency,
        BigDecimal totalAmount,
        String pricingJson,
        List<ReservationItem> items
) {
    public enum Status {PENDING, CONFIRMED, CANCELLED}
}
