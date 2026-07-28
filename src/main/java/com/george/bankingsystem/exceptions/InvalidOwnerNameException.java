package com.george.bankingsystem.exceptions;

public class InvalidOwnerNameException extends RuntimeException {
    public InvalidOwnerNameException(String message) {
        super(message);
    }
}
