package io.github.rogerfilipes.application.mapper;

import io.github.rogerfilipes.application.models.Profile;
import io.github.rogerfilipes.domain.user.model.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ProfileMapper {

    @Mapping(target = "following", ignore = true)
    Profile map(UserData data);
}
