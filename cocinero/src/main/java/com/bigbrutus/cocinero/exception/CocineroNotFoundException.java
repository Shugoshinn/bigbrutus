package com.bigbrutus.cocinero.exception;

public class CocineroNotFoundException extends RuntimeException {
    public CocineroNotFoundException(String message) {
        super(message);
    }
}