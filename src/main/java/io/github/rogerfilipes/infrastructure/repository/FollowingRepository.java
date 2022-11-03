package io.github.rogerfilipes.infrastructure.repository;

import io.github.rogerfilipes.domain.user.model.FollowData;
import io.github.rogerfilipes.infrastructure.repository.jooq.Tables;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.UserFollowings;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.UserFollowingsRecord;
import org.jooq.DSLContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RequestScoped
public class FollowingRepository {
    @Inject
    DSLContext dslContext;

    public void create(FollowData followData) {
        UserFollowingsRecord followRecord = dslContext.newRecord(UserFollowings.USER_FOLLOWINGS);
        followRecord.setUserId(followData.getUser());
        followRecord.setFollowsId(followData.getFollows());
        followRecord.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        followRecord.store();
    }

    public boolean isFollowing(UUID userId, UUID followingId) {
        return dslContext.fetchExists(dslContext.selectFrom(Tables.USER_FOLLOWINGS).where(UserFollowings.USER_FOLLOWINGS.USER_ID.eq(userId).and(UserFollowings.USER_FOLLOWINGS.FOLLOWS_ID.eq(followingId))));
    }

    public void delete(UUID userId, UUID followId) {
        dslContext.deleteFrom(Tables.USER_FOLLOWINGS).where(UserFollowings.USER_FOLLOWINGS.USER_ID.eq(userId).and(UserFollowings.USER_FOLLOWINGS.FOLLOWS_ID.eq(followId)));
    }
}
