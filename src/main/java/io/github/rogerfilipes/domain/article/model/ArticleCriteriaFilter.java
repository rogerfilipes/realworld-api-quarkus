package io.github.rogerfilipes.domain.article.model;

import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.ArticlesRecord;
import org.jooq.TableField;

import java.time.LocalDateTime;

import static io.github.rogerfilipes.infrastructure.repository.jooq.tables.Articles.ARTICLES;

public class ArticleCriteriaFilter {
    String tag;
    String author;
    String favorited;
    Integer limit;
    Integer offset;


    Sort sort;

    public ArticleCriteriaFilter() {
    }

    public ArticleCriteriaFilter(String tag, String author, String favorited, Integer limit, Integer offset) {
        this.tag = tag;
        this.author = author;
        this.favorited = favorited;
        this.limit = limit;
        this.offset = offset;
    }

    public Sort getSort() {
        return sort;
    }

    public ArticleCriteriaFilter setSort(Sort sort) {
        this.sort = sort;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public ArticleCriteriaFilter setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ArticleCriteriaFilter setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getFavorited() {
        return favorited;
    }

    public ArticleCriteriaFilter setFavorited(String favorited) {
        this.favorited = favorited;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public ArticleCriteriaFilter setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public ArticleCriteriaFilter setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public enum SortField {
        CREATION_DATE(ARTICLES.CREATED_AT);

        final TableField field;

        SortField(TableField<ArticlesRecord, LocalDateTime> field) {
            this.field = field;
        }

        public TableField getField() {
            return field;
        }
    }

    public enum SortBy {
        ASC,
        DESC
    }

    public class Sort {
        SortField sortField;
        SortBy sortBy;

        public SortField getSortField() {
            return sortField;
        }

        public Sort setSortField(SortField sortField) {
            this.sortField = sortField;
            return this;
        }

        public SortBy getSortBy() {
            return sortBy;
        }

        public Sort setSortBy(SortBy sortBy) {
            this.sortBy = sortBy;
            return this;
        }
    }
}
