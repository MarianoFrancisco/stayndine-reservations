package com.stayndine.reservations.application.port.in.command;

import java.util.UUID;

public interface CancelReservationUseCase {
    void handle(UUID requesterId, UUID reservationId);
}