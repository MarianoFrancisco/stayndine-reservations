package com.stayndine.reservations.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record ReservationItem(
        UUID id,
        UUID reservationId,
        UUID roomTypeId,
        int quantity,
        BigDecimal nightlyPrice,
        int nights,
        BigDecimal subtotal
) {
}