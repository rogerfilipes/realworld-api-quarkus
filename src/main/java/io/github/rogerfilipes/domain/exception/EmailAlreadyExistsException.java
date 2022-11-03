package io.github.rogerfilipes.domain.exception;

public class EmailAlreadyExistsException extends ApplicationException {
    private static final String MESSAGE = "Email already registered: %s";

    public EmailAlreadyExistsException(String email) {
        super(String.format(MESSAGE, email));
    }
}
