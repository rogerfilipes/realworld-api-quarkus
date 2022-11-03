package io.github.rogerfilipes.domain.comment;

import io.github.rogerfilipes.application.mapper.CommentMapper;
import io.github.rogerfilipes.application.models.Comment;
import io.github.rogerfilipes.application.models.NewCommentRequest;
import io.github.rogerfilipes.domain.article.model.ArticleData;
import io.github.rogerfilipes.domain.authorization.AuthorizationService;
import io.github.rogerfilipes.domain.comment.model.CommentData;
import io.github.rogerfilipes.domain.exception.ArticleNotFoundException;
import io.github.rogerfilipes.domain.exception.CommentNotFoundException;
import io.github.rogerfilipes.domain.profile.ProfilesService;
import io.github.rogerfilipes.infrastructure.repository.ArticlesRepository;
import io.github.rogerfilipes.infrastructure.repository.CommentsRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestScoped
public class CommentsService {

    final ArticlesRepository articlesRepository;
    final CommentsRepository commentsRepository;

    final ProfilesService profilesService;

    final AuthorizationService authorizationService;

    final CommentMapper commentMapper;

    @Inject
    public CommentsService(ArticlesRepository articlesRepository, CommentsRepository commentsRepository, ProfilesService profilesService, AuthorizationService authorizationService, CommentMapper commentMapper) {
        this.articlesRepository = articlesRepository;
        this.commentsRepository = commentsRepository;
        this.profilesService = profilesService;
        this.commentMapper = commentMapper;
        this.authorizationService = authorizationService;
    }

    public Comment createComment(UUID loggedUserId, NewCommentRequest body, String slug) {
        ArticleData articleData = articlesRepository.findArticleBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));

        CommentData data = new CommentData();
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        data.setCreatedAt(now);
        data.setUpdatedAt(now);
        data.setArticle(articleData.getId());
        data.setAuthor(loggedUserId);
        data.setBody(body.getComment().getBody());
        data = commentsRepository.create(data);

        Comment comment = commentMapper.map(data);
        comment.setAuthor(profilesService.getProfileById(loggedUserId, articleData.getAuthor()));

        return comment;
    }

    public void delete(UUID loggedUserId, String slug, Integer id) {

        ArticleData articleData = articlesRepository.findArticleBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));
        CommentData commentData = commentsRepository.findCommentById(id, articleData.getId()).orElseThrow(() -> new CommentNotFoundException(slug, id));


        authorizationService.check(loggedUserId, commentData);
        commentsRepository.delete(commentData.getId());
    }

    public List<Comment> getComments(UUID loggedUserId, String slug) {
        ArticleData articleData = articlesRepository.findArticleBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));

        List<CommentData> commentsByArticleSlug = commentsRepository.findCommentsByArticleSlug(slug);

        List<Comment> comments = new ArrayList<>();
        for (CommentData data : commentsByArticleSlug) {

            Comment comment = commentMapper.map(data);
            comment.setAuthor(profilesService.getProfileById(loggedUserId, articleData.getAuthor()));
            comments.add(comment);
        }

        return comments;

    }
}
