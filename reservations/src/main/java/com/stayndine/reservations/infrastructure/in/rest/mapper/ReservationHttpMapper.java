package com.stayndine.reservations.infrastructure.in.rest.mapper;

import com.stayndine.reservations.domain.model.Reservation;
import com.stayndine.reservations.infrastructure.in.rest.dto.ReservationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationHttpMapper {

    @Mapping(target = "status", expression = "java(r.status().name())")
    ReservationResponse toResponse(Reservation r);

    default ReservationResponse.Item toItem(com.stayndine.reservations.domain.model.ReservationItem i) {
        return new ReservationResponse.Item(i.roomTypeId(), i.quantity(), i.nightlyPrice(), i.nights(), i.subtotal());
    }

    default List<ReservationResponse.Item> toItems(java.util.List<com.stayndine.reservations.domain.model.ReservationItem> list) {
        return list.stream().map(this::toItem).toList();
    }
}