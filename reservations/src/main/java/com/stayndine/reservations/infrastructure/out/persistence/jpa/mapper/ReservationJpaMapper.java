package com.stayndine.reservations.infrastructure.out.persistence.jpa.mapper;

import com.stayndine.reservations.domain.model.Reservation;
import com.stayndine.reservations.domain.model.ReservationItem;
import com.stayndine.reservations.infrastructure.out.persistence.jpa.entity.ReservationEntity;
import com.stayndine.reservations.infrastructure.out.persistence.jpa.entity.ReservationItemEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationJpaMapper {
    default ReservationEntity toEntity(Reservation r) {
        var e = new ReservationEntity();
        e.setId(r.id());
        e.setHotelId(r.hotelId());
        e.setUserId(r.userId());
        e.setCustomerId(r.customerId());
        e.setCode(r.code());
        e.setStatus(r.status());
        e.setCheckIn(r.checkIn());
        e.setCheckOut(r.checkOut());
        e.setGuests(r.guests());
        e.setCurrency(r.currency());
        e.setTotalAmount(r.totalAmount());
        e.setPricingJson(r.pricingJson());

        var items = r.items().stream().map(it -> {
            var ie = new ReservationItemEntity();
            ie.setId(it.id());
            ie.setRoomTypeId(it.roomTypeId());
            ie.setQuantity(it.quantity());
            ie.setNightlyPrice(it.nightlyPrice());
            ie.setNights(it.nights());
            ie.setSubtotal(it.subtotal());
            ie.setReservation(e);
            return ie;
        }).toList();
        e.setItems(items);
        return e;
    }

    default Reservation toDomain(ReservationEntity e) {
        List<ReservationItem> items = e.getItems().stream().map(ie -> new ReservationItem(
                ie.getId(), e.getId(), ie.getRoomTypeId(), ie.getQuantity(), ie.getNightlyPrice(), ie.getNights(), ie.getSubtotal()
        )).toList();
        return new Reservation(e.getId(), e.getHotelId(), e.getUserId(), e.getCustomerId(), e.getCode(),
                e.getStatus(), e.getCheckIn(), e.getCheckOut(), e.getGuests(), e.getCurrency(), e.getTotalAmount(),
                e.getPricingJson(), items);
    }
}