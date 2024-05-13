package com.yandex.app.service;

public class IncorrectDataException extends Exception {
    public IncorrectDataException(final String message) {
        super(message);
    }
}
