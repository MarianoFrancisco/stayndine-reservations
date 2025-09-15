package com.stayndine.reservations.infrastructure.in.error;

public class ValidationException extends RuntimeException {
    public ValidationException(String msg) {
        super(msg);
    }
}