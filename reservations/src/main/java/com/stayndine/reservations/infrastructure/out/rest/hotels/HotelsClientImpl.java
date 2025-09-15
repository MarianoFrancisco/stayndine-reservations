package com.stayndine.reservations.infrastructure.out.rest.hotels;

import com.stayndine.reservations.application.port.out.HotelsClient;
import com.stayndine.reservations.infrastructure.out.rest.hotels.dto.QuoteApiResponse;
import com.stayndine.reservations.infrastructure.out.rest.hotels.dto.RoomTypeSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HotelsClientImpl implements HotelsClient {

    private final RestClient hotelsRestClient;

    @Override
    public QuoteResult quoteRoom(UUID roomTypeId, LocalDate checkIn, LocalDate checkOut) {
        var res = hotelsRestClient.get()
                .uri(uri -> uri.path("/api/v1/room-types/{id}/quote")
                        .queryParam("checkIn", checkIn)
                        .queryParam("checkOut", checkOut)
                        .build(roomTypeId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(QuoteApiResponse.class);

        var nights = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        return new QuoteResult(res.nightlyPrice(), nights, res.currency() != null ? res.currency() : "USD");
    }

    @Override
    public RoomTypeInfo getRoomTypeInfo(UUID hotelId, UUID roomTypeId) {
        var list = hotelsRestClient.get()
                .uri("/api/v1/room-types/by-hotel/{hotelId}", hotelId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<RoomTypeSummary>>() {
                });

        var rt = list.stream()
                .filter(r -> roomTypeId.equals(r.id()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("RoomType not found in hotel listing"));
        return new RoomTypeInfo(rt.totalUnits() != null ? rt.totalUnits() : 0);
    }
}