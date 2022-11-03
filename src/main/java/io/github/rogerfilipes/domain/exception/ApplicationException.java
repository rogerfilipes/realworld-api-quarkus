package io.github.rogerfilipes.domain.exception;

public abstract class ApplicationException extends RuntimeException {
    protected ApplicationException(String message) {
        super(message);
    }
}
