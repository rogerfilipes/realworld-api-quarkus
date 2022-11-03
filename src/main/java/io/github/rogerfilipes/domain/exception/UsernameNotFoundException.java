package io.github.rogerfilipes.domain.exception;

public class UsernameNotFoundException extends ApplicationException {
    private static final String MESSAGE = "Username not found: %s";

    public UsernameNotFoundException(String username) {
        super(String.format(MESSAGE, username));
    }
}
