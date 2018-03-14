package com.codecool.krk.server;

public class NoControlMessageException extends Exception {
    public NoControlMessageException(String message) {
        super(message);
    }

    public NoControlMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoControlMessageException(Throwable cause) {
        super(cause);
    }

    public NoControlMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
