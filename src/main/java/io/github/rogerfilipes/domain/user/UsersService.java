package io.github.rogerfilipes.domain.user;

import io.github.rogerfilipes.application.mapper.UserDataMapper;
import io.github.rogerfilipes.application.models.LoginUserRequest;
import io.github.rogerfilipes.application.models.NewUserRequest;
import io.github.rogerfilipes.application.models.UserRegistered;
import io.github.rogerfilipes.domain.exception.EmailAlreadyExistsException;
import io.github.rogerfilipes.domain.exception.InvalidUserLoginException;
import io.github.rogerfilipes.domain.exception.UsernameAlreadyExistsException;
import io.github.rogerfilipes.domain.user.model.UserData;
import io.github.rogerfilipes.infrastructure.provider.HashProvider;
import io.github.rogerfilipes.infrastructure.provider.JWTTokenProvider;
import io.github.rogerfilipes.infrastructure.repository.UsersRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.UUID;

@RequestScoped
public class UsersService {

    private final UsersRepository usersRepository;
    private final UserDataMapper userDataMapper;

    private final JWTTokenProvider tokenProvider;

    private final HashProvider hashProvider;

    @Inject
    public UsersService(UsersRepository usersRepository, UserDataMapper userDataMapper, JWTTokenProvider tokenProvider, HashProvider hashProvider) {
        this.usersRepository = usersRepository;
        this.userDataMapper = userDataMapper;
        this.tokenProvider = tokenProvider;
        this.hashProvider = hashProvider;
    }

    public UserRegistered register(NewUserRequest request) {

        usersRepository.findUserByEmail(request.getUser().getEmail()).ifPresent(u -> {
            throw new EmailAlreadyExistsException(request.getUser().getEmail());
        });
        usersRepository.findUserByUsername(request.getUser().getEmail()).ifPresent(u -> {
            throw new UsernameAlreadyExistsException(request.getUser().getUsername());
        });

        UserData userData = userDataMapper.mapRequest(request);
        userData.setId(UUID.randomUUID());
        userData.setPassword(hashProvider.encrypt(request.getUser().getPassword()));
        usersRepository.create(userData);

        userData.setToken(tokenProvider.generateToken(userData));
        return userDataMapper.mapRegisteredUser(userData);

    }

    public UserRegistered login(LoginUserRequest request) {
        UserData userData = usersRepository.findUserByEmail(request.getUser().getEmail()).orElseThrow(InvalidUserLoginException::new);

        if (!hashProvider.verify(request.getUser().getPassword(), userData.getPassword()))
            throw new InvalidUserLoginException();

        userData.setToken(tokenProvider.generateToken(userData));
        return userDataMapper.mapRegisteredUser(userData);
    }
}
