package com.stayndine.reservations.application.port.out;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface HotelsClient {
    QuoteResult quoteRoom(UUID roomTypeId, LocalDate checkIn, LocalDate checkOut);

    RoomTypeInfo getRoomTypeInfo(UUID hotelId, UUID roomTypeId);

    record QuoteResult(BigDecimal nightlyPrice, int nights, String currency) {
    }

    record RoomTypeInfo(int totalUnits) {
    }
}