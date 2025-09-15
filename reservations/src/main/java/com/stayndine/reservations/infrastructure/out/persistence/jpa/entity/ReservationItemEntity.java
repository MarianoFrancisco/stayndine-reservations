package com.stayndine.reservations.infrastructure.out.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "reservation_item")
@Getter
@Setter
public class ReservationItemEntity {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationEntity reservation;

    @Column(name = "room_type_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID roomTypeId;
    @Column(nullable = false)
    private int quantity;
    @Column(name = "nightly_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal nightlyPrice;
    @Column(nullable = false)
    private int nights;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;
}