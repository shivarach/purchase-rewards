package com.example.purchaserewards.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
