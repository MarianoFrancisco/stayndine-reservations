package com.stayndine.reservations.infrastructure.out.persistence.jpa.adapter;

import com.stayndine.reservations.application.port.out.ReservationRepository;
import com.stayndine.reservations.domain.model.Reservation;
import com.stayndine.reservations.infrastructure.out.persistence.jpa.mapper.ReservationJpaMapper;
import com.stayndine.reservations.infrastructure.out.persistence.jpa.spring.ReservationJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReservationRepositoryAdapter implements ReservationRepository {

    private final ReservationJpa jpa;
    private final ReservationJpaMapper mapper;

    @Override
    public Reservation save(Reservation r) {
        return mapper.toDomain(jpa.save(mapper.toEntity(r)));
    }

    @Override
    public Optional<Reservation> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Reservation> findByUserId(UUID userId) {
        return jpa.findByUserId(userId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public int countActiveOverlaps(UUID hotelId, UUID roomTypeId, LocalDate checkIn, LocalDate checkOut) {
        return jpa.sumBookedQuantity(hotelId, roomTypeId, checkIn, checkOut);
    }
}