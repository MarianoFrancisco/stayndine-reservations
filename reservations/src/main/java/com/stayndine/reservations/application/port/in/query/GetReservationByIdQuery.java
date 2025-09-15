package com.stayndine.reservations.application.port.in.query;

import com.stayndine.reservations.domain.model.Reservation;

import java.util.UUID;

public interface GetReservationByIdQuery {
    Reservation handle(UUID id);
}