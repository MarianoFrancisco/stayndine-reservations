package com.stayndine.reservations.application.port.in.command;

import java.time.LocalDate;
import java.util.UUID;

public interface CreateReservationUseCase {
    record Command(UUID hotelId, UUID roomTypeId, int quantity,
                   LocalDate checkIn, LocalDate checkOut, int guests, String currency) {
    }

    record Result(UUID reservationId, String code) {
    }

    Result handle(UUID userId, Command cmd);
}