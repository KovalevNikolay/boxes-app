package ru.kovalev.boxesapp.exception;

public class FileDownloadingException extends RuntimeException {
    public FileDownloadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
