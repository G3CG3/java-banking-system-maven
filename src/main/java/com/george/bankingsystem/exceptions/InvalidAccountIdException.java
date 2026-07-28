package com.george.bankingsystem.exceptions;

public class InvalidAccountIdException extends RuntimeException {
  public InvalidAccountIdException(String message) {
    super(message);
  }
}
