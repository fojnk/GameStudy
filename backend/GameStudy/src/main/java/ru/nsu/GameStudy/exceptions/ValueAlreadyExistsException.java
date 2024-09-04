package ru.nsu.GameStudy.exceptions;

public class ValueAlreadyExistsException extends Throwable {
    public ValueAlreadyExistsException() {
        super();
    }

    public ValueAlreadyExistsException(String message) {
        super(message);
    }
}
