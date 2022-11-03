package io.github.rogerfilipes.application.mapper;

import io.github.rogerfilipes.application.models.NewUserRequest;
import io.github.rogerfilipes.application.models.UserRegistered;
import io.github.rogerfilipes.domain.user.model.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserDataMapper {
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "password", source = "user.password")
    UserData mapRequest(NewUserRequest request);

    UserRegistered mapRegisteredUser(UserData userData);
}
