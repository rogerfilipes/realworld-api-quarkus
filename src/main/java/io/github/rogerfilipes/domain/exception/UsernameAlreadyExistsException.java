package io.github.rogerfilipes.domain.exception;

public class UsernameAlreadyExistsException extends ApplicationException {
    private static final String MESSAGE = "Username already registered: %s";

    public UsernameAlreadyExistsException(String email) {
        super(String.format(MESSAGE, email));
    }
}
