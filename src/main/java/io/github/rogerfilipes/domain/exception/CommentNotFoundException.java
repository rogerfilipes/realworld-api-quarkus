package io.github.rogerfilipes.domain.exception;

public class CommentNotFoundException extends ApplicationException {
    private static final String MESSAGE = "Comment not found, slug:  %s; id: %d";

    public CommentNotFoundException(String slug, int id) {
        super(String.format(MESSAGE, slug, id));
    }
}
