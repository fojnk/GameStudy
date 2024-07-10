package ru.nsu.GameStudy.exceptions;

public class UserAlreadyExistsException extends Throwable {
    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
 }