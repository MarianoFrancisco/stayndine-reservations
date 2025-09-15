package com.stayndine.reservations.application.service;

import com.stayndine.reservations.application.port.in.query.GetReservationByIdQuery;
import com.stayndine.reservations.application.port.out.ReservationRepository;
import com.stayndine.reservations.domain.model.Reservation;
import com.stayndine.reservations.infrastructure.in.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetReservationByIdService implements GetReservationByIdQuery {

    private final ReservationRepository repo;

    @Override
    @Transactional(readOnly = true)
    public Reservation handle(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
    }
}