package io.github.rogerfilipes.infrastructure.repository;

import io.github.rogerfilipes.domain.exception.UserNotFoundException;
import io.github.rogerfilipes.domain.user.model.UserData;
import io.github.rogerfilipes.infrastructure.repository.jooq.Tables;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.UsersRecord;
import org.jooq.DSLContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.rogerfilipes.infrastructure.repository.jooq.tables.Users.USERS;

@RequestScoped
public class UsersRepository {

    final DSLContext dslContext;

    @Inject
    public UsersRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void create(UserData userData) {
        UsersRecord usersRecord = dslContext.newRecord(USERS);
        usersRecord.setUsername(userData.getUsername());
        usersRecord.setEmail(userData.getEmail());
        usersRecord.setId(userData.getId());
        usersRecord.setPassword(userData.getPassword());
        usersRecord.setImage(userData.getImage());
        usersRecord.setBio(userData.getBio());
        usersRecord.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        usersRecord.store();
    }

    public List<UserData> getUsers() {
        return dslContext.select().from(Tables.USERS).fetchInto(UserData.class);
    }

    public Optional<UserData> findUserByEmail(String email) {
        return dslContext.select().from(Tables.USERS).where(USERS.EMAIL.equalIgnoreCase(email)).fetchInto(UserData.class).stream().findFirst();
    }

    public Optional<UserData> findUserByUsername(String username) {
        return dslContext.select().from(Tables.USERS).where(USERS.USERNAME.equalIgnoreCase(username)).fetchInto(UserData.class).stream().findFirst();
    }

    public void update(UserData userData) {
        UsersRecord usersRecord = dslContext.select().from(Tables.USERS).where(USERS.ID.equal(userData.getId())).fetchInto(UsersRecord.class).stream().findFirst().orElseThrow(() -> new UserNotFoundException(userData.getId()));

        usersRecord.setUsername(userData.getUsername());
        usersRecord.setEmail(userData.getEmail());
        usersRecord.setId(userData.getId());
        usersRecord.setPassword(userData.getPassword());
        usersRecord.setImage(userData.getImage());
        usersRecord.setBio(userData.getBio());
        usersRecord.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        usersRecord.update();
    }

    public Optional<UserData> findUserById(UUID userId) {
        return dslContext.select().from(Tables.USERS).where(USERS.ID.equal(userId)).fetchInto(UserData.class).stream().findFirst();

    }
}
