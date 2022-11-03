/*
 * This file is generated by jOOQ.
 */
package io.github.rogerfilipes.infrastructure.repository.jooq;


import io.github.rogerfilipes.infrastructure.repository.jooq.tables.ArticleTags;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.Articles;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.Comments;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.Databasechangeloglock;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.Tags;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.UserFavorites;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.UserFollowings;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.Users;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.ArticleTagsRecord;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.ArticlesRecord;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.CommentsRecord;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.DatabasechangeloglockRecord;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.TagsRecord;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.UserFavoritesRecord;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.UserFollowingsRecord;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.UsersRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ArticleTagsRecord> PK_ARTICLE_TAGS = Internal.createUniqueKey(ArticleTags.ARTICLE_TAGS, DSL.name("pk_article_tags"), new TableField[] { ArticleTags.ARTICLE_TAGS.ARTICLE, ArticleTags.ARTICLE_TAGS.TAG }, true);
    public static final UniqueKey<ArticlesRecord> ARTICLES_SLUG_KEY = Internal.createUniqueKey(Articles.ARTICLES, DSL.name("articles_slug_key"), new TableField[] { Articles.ARTICLES.SLUG }, true);
    public static final UniqueKey<ArticlesRecord> PK_ARTICLES = Internal.createUniqueKey(Articles.ARTICLES, DSL.name("pk_articles"), new TableField[] { Articles.ARTICLES.ID }, true);
    public static final UniqueKey<CommentsRecord> PK_COMMENTS = Internal.createUniqueKey(Comments.COMMENTS, DSL.name("pk_comments"), new TableField[] { Comments.COMMENTS.ID }, true);
    public static final UniqueKey<DatabasechangeloglockRecord> DATABASECHANGELOGLOCK_PKEY = Internal.createUniqueKey(Databasechangeloglock.DATABASECHANGELOGLOCK, DSL.name("databasechangeloglock_pkey"), new TableField[] { Databasechangeloglock.DATABASECHANGELOGLOCK.ID }, true);
    public static final UniqueKey<TagsRecord> PK_TAGS = Internal.createUniqueKey(Tags.TAGS, DSL.name("pk_tags"), new TableField[] { Tags.TAGS.ID }, true);
    public static final UniqueKey<TagsRecord> TAGS_TAG_KEY = Internal.createUniqueKey(Tags.TAGS, DSL.name("tags_tag_key"), new TableField[] { Tags.TAGS.TAG }, true);
    public static final UniqueKey<UserFavoritesRecord> PK_USER_FAVORITES = Internal.createUniqueKey(UserFavorites.USER_FAVORITES, DSL.name("pk_user_favorites"), new TableField[] { UserFavorites.USER_FAVORITES.USER_ID, UserFavorites.USER_FAVORITES.ARTICLE }, true);
    public static final UniqueKey<UserFollowingsRecord> PK_USER_FOLLOWS = Internal.createUniqueKey(UserFollowings.USER_FOLLOWINGS, DSL.name("pk_user_follows"), new TableField[] { UserFollowings.USER_FOLLOWINGS.USER_ID, UserFollowings.USER_FOLLOWINGS.FOLLOWS_ID }, true);
    public static final UniqueKey<UsersRecord> PK_USERS = Internal.createUniqueKey(Users.USERS, DSL.name("pk_users"), new TableField[] { Users.USERS.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ArticleTagsRecord, ArticlesRecord> ARTICLE_TAGS__FK_ARTICLE_TAGS_ARTICLE = Internal.createForeignKey(ArticleTags.ARTICLE_TAGS, DSL.name("fk_article_tags_article"), new TableField[] { ArticleTags.ARTICLE_TAGS.ARTICLE }, Keys.PK_ARTICLES, new TableField[] { Articles.ARTICLES.ID }, true);
    public static final ForeignKey<ArticleTagsRecord, TagsRecord> ARTICLE_TAGS__FK_ARTICLE_TAGS_TAGS = Internal.createForeignKey(ArticleTags.ARTICLE_TAGS, DSL.name("fk_article_tags_tags"), new TableField[] { ArticleTags.ARTICLE_TAGS.TAG }, Keys.PK_TAGS, new TableField[] { Tags.TAGS.ID }, true);
    public static final ForeignKey<ArticlesRecord, UsersRecord> ARTICLES__FK_ARTICLES_USERS = Internal.createForeignKey(Articles.ARTICLES, DSL.name("fk_articles_users"), new TableField[] { Articles.ARTICLES.AUTHOR }, Keys.PK_USERS, new TableField[] { Users.USERS.ID }, true);
    public static final ForeignKey<CommentsRecord, ArticlesRecord> COMMENTS__FK_COMMENTS_ARTICLES = Internal.createForeignKey(Comments.COMMENTS, DSL.name("fk_comments_articles"), new TableField[] { Comments.COMMENTS.ARTICLE }, Keys.PK_ARTICLES, new TableField[] { Articles.ARTICLES.ID }, true);
    public static final ForeignKey<CommentsRecord, UsersRecord> COMMENTS__FK_COMMENTS_USERS = Internal.createForeignKey(Comments.COMMENTS, DSL.name("fk_comments_users"), new TableField[] { Comments.COMMENTS.AUTHOR }, Keys.PK_USERS, new TableField[] { Users.USERS.ID }, true);
    public static final ForeignKey<UserFavoritesRecord, ArticlesRecord> USER_FAVORITES__FK_USER_FAVORITES_ARTICLE = Internal.createForeignKey(UserFavorites.USER_FAVORITES, DSL.name("fk_user_favorites_article"), new TableField[] { UserFavorites.USER_FAVORITES.ARTICLE }, Keys.PK_ARTICLES, new TableField[] { Articles.ARTICLES.ID }, true);
    public static final ForeignKey<UserFavoritesRecord, UsersRecord> USER_FAVORITES__FK_USER_FAVORITES_USER = Internal.createForeignKey(UserFavorites.USER_FAVORITES, DSL.name("fk_user_favorites_user"), new TableField[] { UserFavorites.USER_FAVORITES.USER_ID }, Keys.PK_USERS, new TableField[] { Users.USERS.ID }, true);
    public static final ForeignKey<UserFollowingsRecord, UsersRecord> USER_FOLLOWINGS__FK_USER_FOLLOWINGS_USERS_FOLLOWS = Internal.createForeignKey(UserFollowings.USER_FOLLOWINGS, DSL.name("fk_user_followings_users_follows"), new TableField[] { UserFollowings.USER_FOLLOWINGS.FOLLOWS_ID }, Keys.PK_USERS, new TableField[] { Users.USERS.ID }, true);
    public static final ForeignKey<UserFollowingsRecord, UsersRecord> USER_FOLLOWINGS__FK_USER_FOLLOWINGS_USERS_USER = Internal.createForeignKey(UserFollowings.USER_FOLLOWINGS, DSL.name("fk_user_followings_users_user"), new TableField[] { UserFollowings.USER_FOLLOWINGS.USER_ID }, Keys.PK_USERS, new TableField[] { Users.USERS.ID }, true);
}