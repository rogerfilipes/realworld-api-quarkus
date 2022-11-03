package io.github.rogerfilipes.domain.exception;

public class ForbiddenException extends ApplicationException{
    private static final String MESSAGE = "Forbidden to perform requested action %s";


    public ForbiddenException(String message) {
        super(String.format(MESSAGE, message));
    }
}
