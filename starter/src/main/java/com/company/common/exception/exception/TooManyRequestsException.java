package com.company.common.exception.exception;

public class TooManyRequestsException extends RuntimeException {

    public TooManyRequestsException(String message) { super(message); }
    
}
