package io.github.rogerfilipes.domain.tag;

import io.github.rogerfilipes.domain.article.model.ArticleTagsData;
import io.github.rogerfilipes.domain.exception.TagAlreadyExistsException;
import io.github.rogerfilipes.domain.tag.model.TagsData;
import io.github.rogerfilipes.infrastructure.repository.TagsRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class TagsService {

    final TagsRepository tagsRepository;

    @Inject
    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public List<String> getAllTags() {
        List<TagsData> tagsData = tagsRepository.getAllTags();
        return tagsData.stream().map(TagsData::getTag).collect(Collectors.toList());
    }

    public TagsData createTag(String tag) {
        tagsRepository.findTag(tag).ifPresent(t -> {
            throw new TagAlreadyExistsException(tag);
        });
        TagsData data = new TagsData().setId(UUID.randomUUID()).setTag(tag).setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        tagsRepository.create(data);
        return data;
    }

    public Optional<TagsData> findTag(String value) {
        return tagsRepository.findTag(value);
    }

    public TagsData createArticleTag(UUID article, String tag) {
        Optional<TagsData> tagExists = tagsRepository.findTag(tag);

        TagsData tagsData = tagExists.orElseGet(() -> createTag(tag));


        tagsRepository.create(new ArticleTagsData().setTag(tagsData.getId()).setArticle(article));

        return tagsData;
    }

    public List<TagsData> getArticleTags(UUID articleId) {
        return tagsRepository.getArticleTags(articleId);
    }

    public void deleteArticleTags(UUID articleId, List<TagsData> articleTags) {
        for (TagsData tag : articleTags) {
            tagsRepository.deleteArticleTag(articleId, tag);
        }
    }
}
