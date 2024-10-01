package ru.kovalev.boxesapp.exception;

public class OversizedBoxException extends RuntimeException {
    public OversizedBoxException(String message) {
        super(message);
    }
}
