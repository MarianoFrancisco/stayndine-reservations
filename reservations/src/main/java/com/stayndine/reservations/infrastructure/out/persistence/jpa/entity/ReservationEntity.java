package com.stayndine.reservations.infrastructure.out.persistence.jpa.entity;

import com.stayndine.reservations.domain.model.Reservation.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@Getter
@Setter
public class ReservationEntity {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "hotel_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID hotelId;
    @Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
    private UUID userId;
    @Column(name = "customer_id", columnDefinition = "BINARY(16)")
    private UUID customerId;
    @Column(nullable = false, unique = true)
    private String code;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;
    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;
    @Column(nullable = false)
    private int guests;
    @Column(nullable = false, length = 3)
    private String currency;
    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;
    @Column(name = "pricing_json", columnDefinition = "JSON")
    private String pricingJson;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ReservationItemEntity> items;
}