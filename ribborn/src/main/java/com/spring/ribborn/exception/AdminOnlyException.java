package com.spring.ribborn.exception;

public class AdminOnlyException extends RuntimeException {
    public AdminOnlyException(String message) {
        super(message);
    }
}
