package io.github.rogerfilipes.domain.user;

import io.github.rogerfilipes.application.mapper.UserDataMapper;
import io.github.rogerfilipes.application.models.UpdateUser;
import io.github.rogerfilipes.application.models.UserRegistered;
import io.github.rogerfilipes.domain.exception.EmailAlreadyExistsException;
import io.github.rogerfilipes.domain.exception.UserNotFoundException;
import io.github.rogerfilipes.domain.exception.UsernameAlreadyExistsException;
import io.github.rogerfilipes.domain.user.model.UserData;
import io.github.rogerfilipes.infrastructure.provider.HashProvider;
import io.github.rogerfilipes.infrastructure.provider.JWTTokenProvider;
import io.github.rogerfilipes.infrastructure.repository.UsersRepository;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.UUID;

@RequestScoped
public class UserService {

    private final UsersRepository usersRepository;
    private final UserDataMapper userDataMapper;

    private final JWTTokenProvider tokenProvider;

    private final HashProvider hashProvider;

    @Inject
    public UserService(UsersRepository usersRepository, UserDataMapper userDataMapper, JWTTokenProvider tokenProvider, HashProvider hashProvider) {
        this.usersRepository = usersRepository;
        this.userDataMapper = userDataMapper;
        this.tokenProvider = tokenProvider;
        this.hashProvider = hashProvider;
    }

    public UserRegistered getCurrentUser(UUID userId) {
        UserData userData = usersRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userData.setToken(tokenProvider.generateToken(userData));
        return userDataMapper.mapRegisteredUser(userData);
    }


    public UserRegistered updateCurrentUser(UUID userId, UpdateUser request) {
        UserData userData = usersRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (!StringUtils.isBlank(request.getEmail()) && !StringUtils.equals(userData.getEmail(), request.getEmail()))
            usersRepository.findUserByEmail(request.getEmail()).ifPresent(user -> {
                throw new EmailAlreadyExistsException(request.getEmail());
            });

        if (!StringUtils.isBlank(request.getUsername()) && !StringUtils.equals(userData.getUsername(), request.getUsername()))
            usersRepository.findUserByUsername(request.getUsername()).ifPresent(user -> {
                throw new UsernameAlreadyExistsException(request.getUsername());
            });


        userData.setUsername(request.getUsername() != null ? request.getUsername() : userData.getUsername());
        userData.setEmail(request.getEmail() != null ? request.getEmail() : userData.getEmail());
        userData.setPassword(request.getPassword() != null ? hashProvider.encrypt(request.getPassword()) : userData.getPassword());
        userData.setImage(request.getImage());
        userData.setBio(request.getBio());

        usersRepository.update(userData);
        userData.setToken(tokenProvider.generateToken(userData));

        return userDataMapper.mapRegisteredUser(userData);

    }
}
