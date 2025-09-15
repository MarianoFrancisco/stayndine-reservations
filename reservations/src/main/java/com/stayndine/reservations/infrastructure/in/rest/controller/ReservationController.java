package com.stayndine.reservations.infrastructure.in.rest.controller;

import com.stayndine.reservations.application.port.in.command.CancelReservationUseCase;
import com.stayndine.reservations.application.port.in.command.CreateReservationUseCase;
import com.stayndine.reservations.application.port.in.query.GetReservationByIdQuery;
import com.stayndine.reservations.application.port.in.query.ListMyReservationsQuery;
import com.stayndine.reservations.infrastructure.in.rest.dto.ReservationCreateRequest;
import com.stayndine.reservations.infrastructure.in.rest.dto.ReservationResponse;
import com.stayndine.reservations.infrastructure.in.rest.mapper.ReservationHttpMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-jwt")
public class ReservationController {

    private final CreateReservationUseCase create;
    private final CancelReservationUseCase cancel;
    private final GetReservationByIdQuery getById;
    private final ListMyReservationsQuery listMine;
    private final ReservationHttpMapper mapper;

    @PostMapping
    public ResponseEntity<CreateReservationUseCase.Result> create(
            @Valid @RequestBody ReservationCreateRequest body,
            JwtAuthenticationToken auth
    ) {
        var userId = UUID.fromString(auth.getName());
        var res = create.handle(userId, new CreateReservationUseCase.Command(
                body.hotelId(), body.roomTypeId(), body.quantity(),
                body.checkIn(), body.checkOut(), body.guests(), body.currency()
        ));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> byId(@PathVariable UUID id, JwtAuthenticationToken auth) {
        var r = getById.handle(id);
        if (!r.userId().toString().equals(auth.getName())) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(mapper.toResponse(r));
    }

    @GetMapping("/me")
    public ResponseEntity<?> my(JwtAuthenticationToken auth) {
        var list = listMine.handle(UUID.fromString(auth.getName()))
                .stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable UUID id, JwtAuthenticationToken auth) {
        cancel.handle(UUID.fromString(auth.getName()), id);
        return ResponseEntity.noContent().build();
    }
}