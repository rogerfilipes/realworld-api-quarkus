package io.github.rogerfilipes.domain.exception;

public class InvalidUserLoginException extends ApplicationException {
    private static final String MESSAGE = "Invalid email or password";

    public InvalidUserLoginException() {
        super(MESSAGE);
    }
}
