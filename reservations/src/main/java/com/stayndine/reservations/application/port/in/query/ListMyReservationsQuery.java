package com.stayndine.reservations.application.port.in.query;

import com.stayndine.reservations.domain.model.Reservation;

import java.util.List;
import java.util.UUID;

public interface ListMyReservationsQuery {
    List<Reservation> handle(UUID userId);
}