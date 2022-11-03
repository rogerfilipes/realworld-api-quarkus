package io.github.rogerfilipes.infrastructure.repository;

import io.github.rogerfilipes.domain.article.model.ArticleTagsData;
import io.github.rogerfilipes.domain.tag.model.TagsData;
import io.github.rogerfilipes.infrastructure.repository.jooq.Tables;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.ArticleTags;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.Tags;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.ArticleTagsRecord;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.TagsRecord;
import org.jooq.DSLContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class TagsRepository {
    @Inject
    DSLContext dslContext;

    public void create(TagsData tagsData) {
        TagsRecord tagsRecord = dslContext.newRecord(Tables.TAGS);
        tagsRecord.setTag(tagsData.getTag());
        tagsRecord.setId(tagsData.getId());
        tagsRecord.setCreatedAt(tagsData.getCreatedAt());
        tagsRecord.store();
    }

    public Optional<TagsData> findTag(String tag) {
        return dslContext.selectFrom(Tables.TAGS).where(Tags.TAGS.TAG.equalIgnoreCase(tag)).fetchOptionalInto(TagsData.class);
    }

    public void create(ArticleTagsData data) {
        ArticleTagsRecord articleTagRecord = dslContext.newRecord(Tables.ARTICLE_TAGS);
        articleTagRecord.setTag(data.getTag());
        articleTagRecord.setArticle(data.getArticle());
        articleTagRecord.store();
    }

    public List<TagsData> getArticleTags(UUID articleId) {
        return dslContext.select(Tables.TAGS).from(Tables.TAGS).join(ArticleTags.ARTICLE_TAGS).on(ArticleTags.ARTICLE_TAGS.TAG.eq(Tags.TAGS.ID)).where(ArticleTags.ARTICLE_TAGS.ARTICLE.eq(articleId)).fetchInto(TagsData.class);
    }

    public void deleteArticleTag(UUID articleId, TagsData tag) {
        dslContext.delete(ArticleTags.ARTICLE_TAGS).where(ArticleTags.ARTICLE_TAGS.ARTICLE.eq(articleId), ArticleTags.ARTICLE_TAGS.TAG.eq(tag.getId())).execute();
    }

    public List<TagsData> getAllTags() {
        return dslContext.selectFrom(Tables.TAGS).fetchInto(TagsData.class);
    }
}
