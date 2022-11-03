package io.github.rogerfilipes.domain.exception;

public class TagAlreadyExistsException extends ApplicationException {
    private static final String MESSAGE = "Tag already exists: %s";

    public TagAlreadyExistsException(String tag) {
        super(String.format(MESSAGE, tag));
    }
}
