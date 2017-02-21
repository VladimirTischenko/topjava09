package ru.javawebinar.topjava.util.exception;

/**
 * Created by Vladimir on 21.02.2017.
 */
public class FieldCanNotBeEmptyException extends RuntimeException {
    public FieldCanNotBeEmptyException(String message) {
        super(message);
    }
}
