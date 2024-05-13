package com.yandex.app.service;

import java.io.IOException;

public class IncorrectDataException extends Exception {
    public IncorrectDataException(final String message) {
        super(message);
    }
}
