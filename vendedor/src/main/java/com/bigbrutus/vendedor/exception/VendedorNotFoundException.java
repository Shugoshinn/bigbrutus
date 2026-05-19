package com.bigbrutus.vendedor.exception;

public class VendedorNotFoundException extends RuntimeException {
    public VendedorNotFoundException(String message) {
        super(message);
    }
}