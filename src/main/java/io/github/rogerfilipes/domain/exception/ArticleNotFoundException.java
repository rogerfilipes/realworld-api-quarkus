package io.github.rogerfilipes.domain.exception;

public class ArticleNotFoundException extends ApplicationException {
    private static final String MESSAGE = "Article not found, slug:  %s";

    public ArticleNotFoundException(final String articleId) {
        super(String.format(MESSAGE, articleId));
    }
}
