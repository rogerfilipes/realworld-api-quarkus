package io.github.rogerfilipes.infrastructure.repository;

import io.github.rogerfilipes.application.models.PageResult;
import io.github.rogerfilipes.domain.article.model.ArticleCriteriaFilter;
import io.github.rogerfilipes.domain.article.model.ArticleData;
import io.github.rogerfilipes.infrastructure.repository.jooq.Tables;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.ArticlesRecord;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.rogerfilipes.infrastructure.repository.jooq.tables.ArticleTags.ARTICLE_TAGS;
import static io.github.rogerfilipes.infrastructure.repository.jooq.tables.Articles.ARTICLES;
import static io.github.rogerfilipes.infrastructure.repository.jooq.tables.Tags.TAGS;
import static io.github.rogerfilipes.infrastructure.repository.jooq.tables.UserFavorites.USER_FAVORITES;
import static io.github.rogerfilipes.infrastructure.repository.jooq.tables.Users.USERS;

@RequestScoped
public class ArticlesRepository {
    @Inject
    DSLContext dslContext;

    private static SelectJoinStep<?> buildCriteriaQuery(ArticleCriteriaFilter criteriaFilter, SelectJoinStep<?> query) {
        if (StringUtils.isNotBlank(criteriaFilter.getAuthor())) {
            query = query.join(Tables.USERS).on(ARTICLES.AUTHOR.eq(USERS.ID)).and(USERS.USERNAME.equalIgnoreCase(criteriaFilter.getAuthor()));
        }

        if (StringUtils.isNotBlank(criteriaFilter.getTag())) {
            query = query.join(Tables.ARTICLE_TAGS).on(ARTICLES.ID.eq(ARTICLE_TAGS.ARTICLE)).join(TAGS).on(ARTICLE_TAGS.TAG.eq(TAGS.ID)).and(TAGS.TAG.equalIgnoreCase(criteriaFilter.getTag()));
        }

        if (StringUtils.isNotBlank(criteriaFilter.getFavorited())) {
            query = query.join(Tables.USER_FAVORITES).on(ARTICLES.ID.eq(USER_FAVORITES.ARTICLE)).join(USERS).on(USER_FAVORITES.USER_ID.eq(USERS.ID)).and(USERS.USERNAME.equalIgnoreCase(criteriaFilter.getFavorited()));
        }

        return query;
    }

    private SortField getSortField(ArticleCriteriaFilter criteriaFilter) {
        SortField sortField;
        if (criteriaFilter.getSort() == null) {
            sortField = ARTICLES.CREATED_AT.desc();
        }else{
            TableField field = criteriaFilter.getSort().getSortField().getField();
            if (criteriaFilter.getSort().getSortBy() == ArticleCriteriaFilter.SortBy.ASC) {
                sortField= field.asc();
            } else {
                sortField = field.desc();
            }
        }

        return  sortField;
    }

    public void create(ArticleData articleData) {
        ArticlesRecord articlesRecord = dslContext.newRecord(Tables.ARTICLES);
        articlesRecord.setAuthor(articleData.getAuthor());
        articlesRecord.setId(articleData.getId());
        articlesRecord.setBody(articleData.getBody());
        articlesRecord.setDescription(articleData.getDescription());
        articlesRecord.setSlug(articleData.getSlug());
        articlesRecord.setTitle(articleData.getTitle());
        articlesRecord.setCreatedAt(articleData.getCreatedAt());
        articlesRecord.setUpdatedAt(articleData.getUpdatedAt());
        articlesRecord.store();
    }

    public void update(ArticleData article) {
        ArticlesRecord articlesRecord = dslContext.fetchOne(ARTICLES, ARTICLES.ID.eq(article.getId()));
        assert articlesRecord != null;
        articlesRecord.setTitle(article.getTitle());
        articlesRecord.setBody(article.getBody());
        articlesRecord.setDescription(article.getDescription());
        articlesRecord.setSlug(article.getSlug());
        articlesRecord.setUpdatedAt(article.getUpdatedAt());
        articlesRecord.store();
    }

    public Optional<ArticleData> findArticleBySlug(String slug) {
        return dslContext.select().from(Tables.ARTICLES).where(ARTICLES.SLUG.equalIgnoreCase(slug)).fetchOptionalInto(ArticleData.class);
    }

    public void deleteById(UUID id) {
        dslContext.delete(Tables.ARTICLES).where(ARTICLES.ID.eq(id)).execute();
    }


    public PageResult<ArticleData> findAllByCriteria(ArticleCriteriaFilter criteriaFilter) {
        SelectJoinStep<Record1<ArticlesRecord>> query = dslContext.select(Tables.ARTICLES).from(Tables.ARTICLES);
        SelectJoinStep<Record1<Integer>> queryCount = dslContext.selectCount().from(Tables.ARTICLES);

        buildCriteriaQuery(criteriaFilter, query);
        buildCriteriaQuery(criteriaFilter, queryCount);

        List<ArticleData> articleData = query.orderBy(getSortField(criteriaFilter)).offset(criteriaFilter.getOffset()).limit(criteriaFilter.getLimit()).fetchInto(ArticleData.class);
        Integer total = queryCount.fetchOne(0, Integer.class);
        return new PageResult<>(articleData, total);
    }

}
