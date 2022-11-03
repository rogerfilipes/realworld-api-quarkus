package io.github.rogerfilipes.infrastructure.repository;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.sql.DataSource;

public class DslContextProducer {

    private static final Integer MAX_ROWS = 100;
    private final DataSource dataSource;

    @Inject
    public DslContextProducer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Produces
    @RequestScoped
    public DSLContext getDslContext() {
        try {
            return DSL.using(getConfiguration());
        } catch (Exception e) {
            throw new JooqContextProducerException(e);
        }
    }


    private Configuration getConfiguration() {
        Settings settings = new Settings()
                .withExecuteLogging(true)
                .withRenderFormatted(true)
                .withRenderCatalog(true)
                .withRenderSchema(true)
                .withMaxRows(MAX_ROWS)
                .withRenderQuotedNames(RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED)
                .withRenderNameCase(RenderNameCase.LOWER_IF_UNQUOTED);
        return new DefaultConfiguration()
                .set(dataSource)
                .set(SQLDialect.POSTGRES)
                .set(settings);
    }
}
