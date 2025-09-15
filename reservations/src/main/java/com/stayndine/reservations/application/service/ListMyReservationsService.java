package com.stayndine.reservations.application.service;

import com.stayndine.reservations.application.port.in.query.ListMyReservationsQuery;
import com.stayndine.reservations.application.port.out.ReservationRepository;
import com.stayndine.reservations.domain.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListMyReservationsService implements ListMyReservationsQuery {

    private final ReservationRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> handle(UUID userId) {
        return repo.findByUserId(userId);
    }
}