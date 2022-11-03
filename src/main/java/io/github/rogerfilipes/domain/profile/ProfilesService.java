package io.github.rogerfilipes.domain.profile;

import io.github.rogerfilipes.application.mapper.ProfileMapper;
import io.github.rogerfilipes.application.models.Profile;
import io.github.rogerfilipes.domain.exception.UserNotFoundException;
import io.github.rogerfilipes.domain.exception.UsernameNotFoundException;
import io.github.rogerfilipes.domain.user.model.FollowData;
import io.github.rogerfilipes.domain.user.model.UserData;
import io.github.rogerfilipes.infrastructure.repository.FollowingRepository;
import io.github.rogerfilipes.infrastructure.repository.UsersRepository;

import javax.enterprise.context.RequestScoped;
import java.util.Objects;
import java.util.UUID;

@RequestScoped
public class ProfilesService {

    final UsersRepository usersRepository;
    final FollowingRepository followingRepository;
    final ProfileMapper profileMapper;


    public ProfilesService(UsersRepository usersRepository, FollowingRepository followingRepository, ProfileMapper profileMapper) {
        this.usersRepository = usersRepository;
        this.profileMapper = profileMapper;
        this.followingRepository = followingRepository;
    }

    public Profile followUserByUsername(UUID user, String username) {
        UserData userData = usersRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        if (!followingRepository.isFollowing(user, userData.getId())) {
            followingRepository.create(new FollowData().setUser(user).setFollows(userData.getId()));
        }

        Profile profile = profileMapper.map(userData);
        profile.setFollowing(true);


        return profile;
    }

    public Profile getProfileByUsername(UUID userId, String username) {
        UserData userData = usersRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        Profile profile = profileMapper.map(userData);
        profile.setFollowing(userId != null && followingRepository.isFollowing(userId, userData.getId()));
        return profile;
    }

    public Profile getProfileByUsername(String username) {
        UserData userData = usersRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        Profile profile = profileMapper.map(userData);
        profile.setFollowing(false);
        return profile;
    }

    public Profile unfollowUserByUsername(UUID user, String username) {

        UserData userData = usersRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        if (!followingRepository.isFollowing(user, userData.getId())) {
            followingRepository.delete(user, userData.getId());
        }

        Profile profile = profileMapper.map(userData);
        profile.setFollowing(false);
        return profile;
    }

    public Profile getProfileById(UUID userId, UUID targetUserId) {
        Objects.requireNonNull(targetUserId);
        UserData userData = usersRepository.findUserById(targetUserId).orElseThrow(() -> new UserNotFoundException(targetUserId));

        Profile profile = profileMapper.map(userData);
        profile.setFollowing(userId != null && followingRepository.isFollowing(userId, userData.getId()));
        return profile;
    }
}
