package io.github.rogerfilipes.infrastructure.repository;

import io.github.rogerfilipes.domain.comment.model.CommentData;
import io.github.rogerfilipes.infrastructure.repository.jooq.Tables;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.CommentsRecord;
import org.jooq.DSLContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.rogerfilipes.infrastructure.repository.jooq.tables.Articles.ARTICLES;
import static io.github.rogerfilipes.infrastructure.repository.jooq.tables.Comments.COMMENTS;

@RequestScoped
public class CommentsRepository {
    @Inject
    DSLContext dslContext;

    public CommentData create(CommentData commentData) {
        CommentsRecord articlesRecord = dslContext.newRecord(Tables.COMMENTS);
        articlesRecord.setCreatedAt(commentData.getCreatedAt());
        articlesRecord.setUpdatedAt(commentData.getUpdatedAt());
        articlesRecord.setAuthor(commentData.getAuthor());
        articlesRecord.setArticle(commentData.getArticle());
        articlesRecord.setBody(commentData.getBody());
        articlesRecord.store();

        return commentData.setId(articlesRecord.getId());

    }

    public List<CommentData> findCommentsByArticleSlug(String slug) {
        return dslContext.select(Tables.COMMENTS).from(Tables.COMMENTS).join(Tables.ARTICLES).on(COMMENTS.ARTICLE.eq(ARTICLES.ID)).where(ARTICLES.SLUG.eq(slug)).fetchInto(CommentData.class);
    }

    public Optional<CommentData> findCommentById(Integer commentId, UUID articleId) {
        return dslContext.select(Tables.COMMENTS).from(Tables.COMMENTS).where(COMMENTS.ID.eq(commentId)).and(COMMENTS.ARTICLE.eq(articleId)).fetchOptionalInto(CommentData.class);
    }


    public void delete(int id) {
        dslContext.delete(Tables.COMMENTS).where(COMMENTS.ID.eq(id)).execute();
    }
}
