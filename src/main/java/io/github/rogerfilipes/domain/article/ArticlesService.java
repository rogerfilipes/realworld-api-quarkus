package io.github.rogerfilipes.domain.article;

import io.github.rogerfilipes.application.mapper.ArticleDataMapper;
import io.github.rogerfilipes.application.models.*;
import io.github.rogerfilipes.domain.article.model.ArticleCriteriaFilter;
import io.github.rogerfilipes.domain.article.model.ArticleData;
import io.github.rogerfilipes.domain.authorization.AuthorizationService;
import io.github.rogerfilipes.domain.exception.ArticleNotFoundException;
import io.github.rogerfilipes.domain.profile.ProfilesService;
import io.github.rogerfilipes.domain.tag.TagsService;
import io.github.rogerfilipes.domain.tag.model.TagsData;
import io.github.rogerfilipes.domain.user.model.FavoriteData;
import io.github.rogerfilipes.infrastructure.repository.ArticlesRepository;
import io.github.rogerfilipes.infrastructure.repository.FavoriteRepository;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.RequestScoped;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class ArticlesService {

    final ArticleDataMapper articleDataMapper;

    final ArticlesRepository articlesRepository;

    final SlugService slugService;

    final ProfilesService profilesService;

    final TagsService tagsService;

    final FavoriteRepository favoriteRepository;

    final AuthorizationService authorizationService;


    public ArticlesService(ArticleDataMapper articleDataMapper, ArticlesRepository articlesRepository, SlugService slugService, ProfilesService profilesService, TagsService tagsService, FavoriteRepository favoriteRepository, AuthorizationService authorizationService) {
        this.articleDataMapper = articleDataMapper;
        this.articlesRepository = articlesRepository;
        this.slugService = slugService;
        this.profilesService = profilesService;
        this.tagsService = tagsService;
        this.favoriteRepository = favoriteRepository;
        this.authorizationService = authorizationService;
    }

    public Article createArticle(UUID userId, NewArticleRequest request) {

        Profile profile = profilesService.getProfileById(userId, userId);

        ArticleData articleData = articleDataMapper.mapRequest(request.getArticle());
        articleData.setId(UUID.randomUUID());
        articleData.setSlug(slugService.slugify(articleData.getTitle()));
        articleData.setAuthor(userId);
        LocalDateTime creationDate = LocalDateTime.now(ZoneId.of("UTC"));
        articleData.setCreatedAt(creationDate);
        articleData.setUpdatedAt(creationDate);
        articlesRepository.create(articleData);

        List<TagsData> tags = request.getArticle().getTagList().stream().map(tag -> tagsService.createArticleTag(articleData.getId(), tag)).collect(Collectors.toList());

        Article article = articleDataMapper.articleData(articleData);
        article.setTagList(tags.stream().map(TagsData::getTag).collect(Collectors.toList()));
        article.setAuthor(profile);

        return article;
    }


    public void deleteArticle(UUID loggedUserId, String slug) {
        ArticleData article = articlesRepository.findArticleBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));

        authorizationService.check(loggedUserId, article);

        tagsService.deleteArticleTags(article.getId(), tagsService.getArticleTags(article.getId()));
        articlesRepository.deleteById(article.getId());
    }


    public Article getArticle(UUID loggedUserId, String slug) {
        ArticleData articleData = articlesRepository.findArticleBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));

        Article article = articleDataMapper.articleData(articleData);
        article = enrichArticle(loggedUserId, articleData, article);
        return article;
    }


    public PageResult<Article> getArticles(UUID loggedUserId, ArticleCriteriaFilter criteriaFilter) {
        PageResult<ArticleData> pageResult = articlesRepository.findAllByCriteria(criteriaFilter);

        return resolvePageResult(loggedUserId, pageResult);
    }

    private PageResult<Article> resolvePageResult(UUID loggedUserId, PageResult<ArticleData> pageResult) {
        List<Article> articles = pageResult.getResult().stream().map(articleData -> {
            Article article = articleDataMapper.articleData(articleData);
            article = enrichArticle(loggedUserId, articleData, article);
            return article;
        }).collect(Collectors.toList());

        return new PageResult<>(articles, pageResult.getTotal());
    }


    public PageResult<Article> getArticlesFeed(UUID loggedUserId, Integer limit, Integer offset) {
        PageResult<ArticleData> pageResult = articlesRepository.findAllByCriteria(new ArticleCriteriaFilter().setLimit(limit).setOffset(offset));
        return resolvePageResult(loggedUserId, pageResult);
    }

    public Article updateArticle(UUID loggedUserId, UpdateArticleRequest request, String slug) {
        ArticleData articleData = articlesRepository.findArticleBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));

        authorizationService.check(loggedUserId, articleData);

        articleData = articleDataMapper.update(articleData, request.getArticle());

        if (StringUtils.isNotBlank(request.getArticle().getTitle())) {
            articleData.setSlug(slugService.slugify(articleData.getTitle()));
        }

        articleData.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        articlesRepository.update(articleData);


        Article article = articleDataMapper.articleData(articleData);
        article = enrichArticle(loggedUserId, articleData, article);
        return article;

    }


    public Article favorite(UUID loggedUserId, String slug) {
        ArticleData articleData = articlesRepository.findArticleBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));

        FavoriteData favoriteData = new FavoriteData();
        favoriteData.setUser(loggedUserId);
        favoriteData.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        favoriteData.setArticle(articleData.getId());

        favoriteRepository.create(favoriteData);


        Article article = articleDataMapper.articleData(articleData);
        article = enrichArticle(loggedUserId, articleData, article);
        return article;
    }


    public Article deleteFavorite(UUID loggedUserId, String slug) {
        ArticleData articleData = articlesRepository.findArticleBySlug(slug).orElseThrow(() -> new ArticleNotFoundException(slug));

        favoriteRepository.delete(loggedUserId, articleData.getId());

        Article article = articleDataMapper.articleData(articleData);
        article = enrichArticle(loggedUserId, articleData, article);
        return article;
    }

    private Article enrichArticle(UUID loggedUserId, ArticleData articleData, Article article) {
        List<TagsData> articleTags = tagsService.getArticleTags(articleData.getId());

        article.setTagList(articleTags.stream().map(TagsData::getTag).sorted(Comparator.naturalOrder()).collect(Collectors.toList()));
        article.setAuthor(profilesService.getProfileById(loggedUserId, articleData.getAuthor()));
        article.setFavorited(favoriteRepository.exists(loggedUserId, articleData.getId()));
        article.setFavoritesCount(favoriteRepository.countArticleFavorite(articleData.getId()));
        return article;
    }
}
