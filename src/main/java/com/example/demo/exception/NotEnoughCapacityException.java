package com.example.demo.exception;

public class NotEnoughCapacityException extends RuntimeException {
    public NotEnoughCapacityException(String message) {
        super(message);
    }
}
