/*
 * This file is generated by jOOQ.
 */
package io.github.rogerfilipes.infrastructure.repository.jooq.tables;


import io.github.rogerfilipes.infrastructure.repository.jooq.Keys;
import io.github.rogerfilipes.infrastructure.repository.jooq.Public;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.ArticlesRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function8;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row8;
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
public class Articles extends TableImpl<ArticlesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.articles</code>
     */
    public static final Articles ARTICLES = new Articles();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ArticlesRecord> getRecordType() {
        return ArticlesRecord.class;
    }

    /**
     * The column <code>public.articles.id</code>.
     */
    public final TableField<ArticlesRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.articles.slug</code>.
     */
    public final TableField<ArticlesRecord, String> SLUG = createField(DSL.name("slug"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.articles.title</code>.
     */
    public final TableField<ArticlesRecord, String> TITLE = createField(DSL.name("title"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.articles.description</code>.
     */
    public final TableField<ArticlesRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.articles.body</code>.
     */
    public final TableField<ArticlesRecord, String> BODY = createField(DSL.name("body"), SQLDataType.VARCHAR(512).nullable(false), this, "");

    /**
     * The column <code>public.articles.author</code>.
     */
    public final TableField<ArticlesRecord, UUID> AUTHOR = createField(DSL.name("author"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.articles.created_at</code>.
     */
    public final TableField<ArticlesRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.articles.updated_at</code>.
     */
    public final TableField<ArticlesRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.LOCALDATETIME(6), this, "");

    private Articles(Name alias, Table<ArticlesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Articles(Name alias, Table<ArticlesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.articles</code> table reference
     */
    public Articles(String alias) {
        this(DSL.name(alias), ARTICLES);
    }

    /**
     * Create an aliased <code>public.articles</code> table reference
     */
    public Articles(Name alias) {
        this(alias, ARTICLES);
    }

    /**
     * Create a <code>public.articles</code> table reference
     */
    public Articles() {
        this(DSL.name("articles"), null);
    }

    public <O extends Record> Articles(Table<O> child, ForeignKey<O, ArticlesRecord> key) {
        super(child, key, ARTICLES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<ArticlesRecord> getPrimaryKey() {
        return Keys.PK_ARTICLES;
    }

    @Override
    public List<UniqueKey<ArticlesRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.ARTICLES_SLUG_KEY);
    }

    @Override
    public List<ForeignKey<ArticlesRecord, ?>> getReferences() {
        return Arrays.asList(Keys.ARTICLES__FK_ARTICLES_USERS);
    }

    private transient Users _users;

    /**
     * Get the implicit join path to the <code>public.users</code> table.
     */
    public Users users() {
        if (_users == null)
            _users = new Users(this, Keys.ARTICLES__FK_ARTICLES_USERS);

        return _users;
    }

    @Override
    public Articles as(String alias) {
        return new Articles(DSL.name(alias), this);
    }

    @Override
    public Articles as(Name alias) {
        return new Articles(alias, this);
    }

    @Override
    public Articles as(Table<?> alias) {
        return new Articles(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Articles rename(String name) {
        return new Articles(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Articles rename(Name name) {
        return new Articles(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Articles rename(Table<?> name) {
        return new Articles(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<UUID, String, String, String, String, UUID, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function8<? super UUID, ? super String, ? super String, ? super String, ? super String, ? super UUID, ? super LocalDateTime, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function8<? super UUID, ? super String, ? super String, ? super String, ? super String, ? super UUID, ? super LocalDateTime, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
