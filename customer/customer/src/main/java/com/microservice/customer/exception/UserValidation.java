package com.microservice.customer.exception;

public class UserValidation extends RuntimeException {

    public UserValidation(String message) {
        super(message);
    }
}