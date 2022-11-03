/*
 * This file is generated by jOOQ.
 */
package io.github.rogerfilipes.infrastructure.repository.jooq.tables;


import io.github.rogerfilipes.infrastructure.repository.jooq.Keys;
import io.github.rogerfilipes.infrastructure.repository.jooq.Public;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.CommentsRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function6;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Comments extends TableImpl<CommentsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.comments</code>
     */
    public static final Comments COMMENTS = new Comments();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CommentsRecord> getRecordType() {
        return CommentsRecord.class;
    }

    /**
     * The column <code>public.comments.id</code>.
     */
    public final TableField<CommentsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.comments.body</code>.
     */
    public final TableField<CommentsRecord, String> BODY = createField(DSL.name("body"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.comments.article</code>.
     */
    public final TableField<CommentsRecord, UUID> ARTICLE = createField(DSL.name("article"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.comments.author</code>.
     */
    public final TableField<CommentsRecord, UUID> AUTHOR = createField(DSL.name("author"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.comments.created_at</code>.
     */
    public final TableField<CommentsRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.comments.updated_at</code>.
     */
    public final TableField<CommentsRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.LOCALDATETIME(6), this, "");

    private Comments(Name alias, Table<CommentsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Comments(Name alias, Table<CommentsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.comments</code> table reference
     */
    public Comments(String alias) {
        this(DSL.name(alias), COMMENTS);
    }

    /**
     * Create an aliased <code>public.comments</code> table reference
     */
    public Comments(Name alias) {
        this(alias, COMMENTS);
    }

    /**
     * Create a <code>public.comments</code> table reference
     */
    public Comments() {
        this(DSL.name("comments"), null);
    }

    public <O extends Record> Comments(Table<O> child, ForeignKey<O, CommentsRecord> key) {
        super(child, key, COMMENTS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<CommentsRecord, Integer> getIdentity() {
        return (Identity<CommentsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<CommentsRecord> getPrimaryKey() {
        return Keys.PK_COMMENTS;
    }

    @Override
    public List<ForeignKey<CommentsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.COMMENTS__FK_COMMENTS_ARTICLES, Keys.COMMENTS__FK_COMMENTS_USERS);
    }

    private transient Articles _articles;
    private transient Users _users;

    /**
     * Get the implicit join path to the <code>public.articles</code> table.
     */
    public Articles articles() {
        if (_articles == null)
            _articles = new Articles(this, Keys.COMMENTS__FK_COMMENTS_ARTICLES);

        return _articles;
    }

    /**
     * Get the implicit join path to the <code>public.users</code> table.
     */
    public Users users() {
        if (_users == null)
            _users = new Users(this, Keys.COMMENTS__FK_COMMENTS_USERS);

        return _users;
    }

    @Override
    public Comments as(String alias) {
        return new Comments(DSL.name(alias), this);
    }

    @Override
    public Comments as(Name alias) {
        return new Comments(alias, this);
    }

    @Override
    public Comments as(Table<?> alias) {
        return new Comments(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Comments rename(String name) {
        return new Comments(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Comments rename(Name name) {
        return new Comments(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Comments rename(Table<?> name) {
        return new Comments(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, String, UUID, UUID, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function6<? super Integer, ? super String, ? super UUID, ? super UUID, ? super LocalDateTime, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function6<? super Integer, ? super String, ? super UUID, ? super UUID, ? super LocalDateTime, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
