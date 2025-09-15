package com.stayndine.reservations.infrastructure.out.persistence.jpa.spring;

import com.stayndine.reservations.infrastructure.out.persistence.jpa.entity.ReservationItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationItemJpa extends JpaRepository<ReservationItemEntity, UUID> {
}