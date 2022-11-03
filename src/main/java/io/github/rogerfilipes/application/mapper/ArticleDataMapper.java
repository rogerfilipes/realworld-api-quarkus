package io.github.rogerfilipes.application.mapper;

import io.github.rogerfilipes.application.models.Article;
import io.github.rogerfilipes.application.models.NewArticle;
import io.github.rogerfilipes.application.models.UpdateArticle;
import io.github.rogerfilipes.domain.article.model.ArticleData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapConfig.class, uses = {ProfileMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ArticleDataMapper {

    ArticleData mapRequest(NewArticle newArticle);

    @Mapping(target = "author", ignore = true)
    Article articleData(ArticleData data);

    ArticleData update(@MappingTarget ArticleData target, UpdateArticle source);
}
