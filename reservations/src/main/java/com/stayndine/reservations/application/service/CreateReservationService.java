package com.stayndine.reservations.application.service;

import com.stayndine.reservations.application.port.in.command.CreateReservationUseCase;
import com.stayndine.reservations.application.port.out.HotelsClient;
import com.stayndine.reservations.application.port.out.ReservationRepository;
import com.stayndine.reservations.domain.model.Reservation;
import com.stayndine.reservations.domain.model.ReservationItem;
import com.stayndine.reservations.infrastructure.in.error.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateReservationService implements CreateReservationUseCase {
    private final ReservationRepository repo;
    private final HotelsClient hotels;

    @Override
    @Transactional
    public Result handle(UUID userId, Command cmd) {
        if (!cmd.checkOut().isAfter(cmd.checkIn()))
            throw new ValidationException("check_out must be after check_in");
        if (cmd.quantity() <= 0)
            throw new ValidationException("quantity must be > 0");

        var quote = hotels.quoteRoom(cmd.roomTypeId(), cmd.checkIn(), cmd.checkOut());
        if (quote.nights() <= 0)
            throw new ValidationException("nights must be > 0");

        var info = hotels.getRoomTypeInfo(cmd.hotelId(), cmd.roomTypeId());
        var overlaps = repo.countActiveOverlaps(cmd.hotelId(), cmd.roomTypeId(), cmd.checkIn(), cmd.checkOut());
        if (overlaps + cmd.quantity() > info.totalUnits())
            throw new ValidationException("no availability for given dates");

        var nightly = quote.nightlyPrice();
        var itemSubtotal = nightly
                .multiply(BigDecimal.valueOf(quote.nights()))
                .multiply(BigDecimal.valueOf(cmd.quantity()));

        var item = new ReservationItem(
                UUID.randomUUID(),
                null,
                cmd.roomTypeId(),
                cmd.quantity(),
                nightly,
                quote.nights(),
                itemSubtotal
        );

        var pricingJson = "{\"source\":\"hotels-quote\",\"roomTypeId\":\"" + cmd.roomTypeId() + "\"}";
        var res = new Reservation(
                UUID.randomUUID(),
                cmd.hotelId(),
                userId,
                null,
                generateCode(),
                Reservation.Status.CONFIRMED,
                cmd.checkIn(),
                cmd.checkOut(),
                cmd.guests(),
                quote.currency() != null ? quote.currency() : cmd.currency(),
                itemSubtotal,
                pricingJson,
                List.of(item)
        );

        var saved = repo.save(res);
        return new Result(saved.id(), saved.code());
    }

    private String generateCode() {
        return "R-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}