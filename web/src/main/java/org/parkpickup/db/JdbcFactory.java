package org.parkpickup.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.parkpickup.db.DataSourceFactory.appDbName;

@Component
public class JdbcFactory {
    @Inject
    private DataSourceFactory dataSourceFactory;

    @Bean
    @DependsOn("persistenceInit")
    public JdbcTemplate initJdbcTemplate() {
        return new JdbcTemplate(dataSourceFactory.getDataSource(appDbName));
    }

    @Bean
    @DependsOn("persistenceInit")
    public SimpleJdbcInsert initSimpleJdbcInsert() {
        return new SimpleJdbcInsert(dataSourceFactory.getDataSource(appDbName));
    }
}
