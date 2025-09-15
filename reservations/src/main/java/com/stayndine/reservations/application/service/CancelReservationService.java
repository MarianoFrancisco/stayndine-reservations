package com.stayndine.reservations.application.service;

import com.stayndine.reservations.application.port.in.command.CancelReservationUseCase;
import com.stayndine.reservations.application.port.out.ReservationRepository;
import com.stayndine.reservations.domain.model.Reservation;
import com.stayndine.reservations.infrastructure.in.error.ForbiddenException;
import com.stayndine.reservations.infrastructure.in.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelReservationService implements CancelReservationUseCase {
    private final ReservationRepository repo;

    @Override
    @Transactional
    public void handle(UUID requesterId, UUID reservationId) {
        var r = repo.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (!r.userId().equals(requesterId)) throw new ForbiddenException("not owner");
        if (r.status() == Reservation.Status.CANCELLED) return;

        var cancelled = new Reservation(
                r.id(), r.hotelId(), r.userId(), r.customerId(), r.code(),
                Reservation.Status.CANCELLED, r.checkIn(), r.checkOut(), r.guests(),
                r.currency(), r.totalAmount(), r.pricingJson(), r.items()
        );
        repo.save(cancelled);
    }
}
