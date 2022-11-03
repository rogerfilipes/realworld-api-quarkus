package io.github.rogerfilipes.application.mapper;

import io.github.rogerfilipes.application.models.Comment;
import io.github.rogerfilipes.domain.comment.model.CommentData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapConfig.class)
public interface CommentMapper {

    @Mapping(target = "author", ignore = true)
    Comment map(CommentData data);

}
