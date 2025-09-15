package com.stayndine.reservations.application.port.out;

import com.stayndine.reservations.domain.model.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    Reservation save(Reservation r);

    Optional<Reservation> findById(UUID id);

    List<Reservation> findByUserId(UUID userId);

    int countActiveOverlaps(UUID hotelId, UUID roomTypeId, LocalDate checkIn, LocalDate checkOut);
}