/*
 * This file is generated by jOOQ.
 */
package io.github.rogerfilipes.infrastructure.repository.jooq.tables;


import io.github.rogerfilipes.infrastructure.repository.jooq.Keys;
import io.github.rogerfilipes.infrastructure.repository.jooq.Public;
import io.github.rogerfilipes.infrastructure.repository.jooq.tables.records.TagsRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function3;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row3;
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
public class Tags extends TableImpl<TagsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.tags</code>
     */
    public static final Tags TAGS = new Tags();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TagsRecord> getRecordType() {
        return TagsRecord.class;
    }

    /**
     * The column <code>public.tags.id</code>.
     */
    public final TableField<TagsRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.tags.tag</code>.
     */
    public final TableField<TagsRecord, String> TAG = createField(DSL.name("tag"), SQLDataType.VARCHAR(125).nullable(false), this, "");

    /**
     * The column <code>public.tags.created_at</code>.
     */
    public final TableField<TagsRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    private Tags(Name alias, Table<TagsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Tags(Name alias, Table<TagsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.tags</code> table reference
     */
    public Tags(String alias) {
        this(DSL.name(alias), TAGS);
    }

    /**
     * Create an aliased <code>public.tags</code> table reference
     */
    public Tags(Name alias) {
        this(alias, TAGS);
    }

    /**
     * Create a <code>public.tags</code> table reference
     */
    public Tags() {
        this(DSL.name("tags"), null);
    }

    public <O extends Record> Tags(Table<O> child, ForeignKey<O, TagsRecord> key) {
        super(child, key, TAGS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<TagsRecord> getPrimaryKey() {
        return Keys.PK_TAGS;
    }

    @Override
    public List<UniqueKey<TagsRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.TAGS_TAG_KEY);
    }

    @Override
    public Tags as(String alias) {
        return new Tags(DSL.name(alias), this);
    }

    @Override
    public Tags as(Name alias) {
        return new Tags(alias, this);
    }

    @Override
    public Tags as(Table<?> alias) {
        return new Tags(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Tags rename(String name) {
        return new Tags(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Tags rename(Name name) {
        return new Tags(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Tags rename(Table<?> name) {
        return new Tags(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, String, LocalDateTime> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function3<? super UUID, ? super String, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function3<? super UUID, ? super String, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}