package io.github.rogerfilipes.domain.exception;

import java.util.UUID;

public class UserNotFoundException extends ApplicationException {
    private static final String MESSAGE = "User not found: %s";

    public UserNotFoundException(UUID id) {
        super(String.format(MESSAGE, id));
    }
}
