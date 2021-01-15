package com.bank.app.domain.exception;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Class<?> aClass, String id) {
        super(String.format("Entity %s with id %s not found", aClass.getSimpleName(), id));
    }

    public NotFoundException(Class<?> aClass, long id) {
        super(String.format("Entity %s with id %d not found", aClass.getSimpleName(), id));
    }
}
