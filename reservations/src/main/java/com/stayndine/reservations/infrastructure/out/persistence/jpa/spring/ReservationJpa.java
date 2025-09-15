package com.stayndine.reservations.infrastructure.out.persistence.jpa.spring;

import com.stayndine.reservations.infrastructure.out.persistence.jpa.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservationJpa extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findByUserId(UUID userId);

    @Query("""
              select coalesce(sum(ri.quantity),0) from ReservationEntity r
                join r.items ri
               where r.hotelId = :hotelId
                 and ri.roomTypeId = :roomTypeId
                 and r.status <> 'CANCELLED'
                 and r.checkIn < :checkOut and r.checkOut > :checkIn
            """)
    int sumBookedQuantity(UUID hotelId, UUID roomTypeId, LocalDate checkIn, LocalDate checkOut);
}