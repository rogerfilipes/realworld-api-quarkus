package io.github.rogerfilipes.infrastructure.repository;

import io.github.rogerfilipes.domain.user.model.FavoriteData;
import io.github.rogerfilipes.infrastructure.repository.jooq.Tables;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.UserFavoritesRecord;
import org.jooq.DSLContext;

import javax.enterprise.context.RequestScoped;
import java.util.UUID;

@RequestScoped
public class FavoriteRepository {

    final DSLContext dslContext;

    public FavoriteRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void create(FavoriteData favoriteData) {
        UserFavoritesRecord userFavoritesRecord = dslContext.newRecord(Tables.USER_FAVORITES);

        userFavoritesRecord.setCreatedAt(favoriteData.getCreatedAt());
        userFavoritesRecord.setArticle(favoriteData.getArticle());
        userFavoritesRecord.setUserId(favoriteData.getUser());
        userFavoritesRecord.store();
    }

    public Boolean exists(UUID loggedUserId, UUID id) {
        return dslContext.select().from(Tables.USER_FAVORITES).where(Tables.USER_FAVORITES.USER_ID.eq(loggedUserId)).and(Tables.USER_FAVORITES.ARTICLE.eq(id)).fetchOptionalInto(FavoriteData.class).isPresent();
    }

    public void delete(UUID loggedUserId, UUID id) {
        dslContext.delete(Tables.USER_FAVORITES).where(Tables.USER_FAVORITES.USER_ID.eq(loggedUserId)).and(Tables.USER_FAVORITES.ARTICLE.eq(id)).execute();
    }

    public int countArticleFavorite(UUID id) {
        return dslContext.selectCount().from(Tables.USER_FAVORITES).where(Tables.USER_FAVORITES.ARTICLE.eq(id)).fetchOne(0, int.class);
    }
}
