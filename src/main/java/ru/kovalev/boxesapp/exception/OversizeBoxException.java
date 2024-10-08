package ru.kovalev.boxesapp.exception;

public class OversizeBoxException extends RuntimeException {
    public OversizeBoxException(String message) {
        super(message);
    }
}
