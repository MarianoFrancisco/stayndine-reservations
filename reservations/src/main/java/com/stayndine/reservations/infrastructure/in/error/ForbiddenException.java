package com.stayndine.reservations.infrastructure.in.error;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String msg) {
        super(msg);
    }
}