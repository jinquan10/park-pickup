package org.parkpickup.db.init;

import org.parkpickup.Util;
import org.parkpickup.db.DataSourceFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;

@Component
@DependsOn("dbCreation")
public class PersistenceInit {
    private Util util;
    private DataSourceFactory dataSourceFactory;

    private final String setupStatement;
    private final String setupFunctionsStatement;
    private final String teardownAllTablesSql;
    private final String teardownDynamicTablesSql;
    private final String teardownFunctionsStatement;

    private final JdbcTemplate jdbcTemplate;

    @Inject
    public PersistenceInit(
            Util util,
            DataSourceFactory dataSourceFactory) throws IOException, SQLException {
        this.util = util;
        this.dataSourceFactory = dataSourceFactory;

        this.setupStatement = this.util.getSqlStatementFromFile("sql/tables/setup.sql");
        this.setupFunctionsStatement = this.util.getSqlStatementFromFile("sql/functions/setup_functions.sql");
        this.teardownAllTablesSql = this.util.getSqlStatementFromFile("sql/tables/teardown_all.sql");
        this.teardownDynamicTablesSql = this.util.getSqlStatementFromFile("sql/tables/teardown_dynamic.sql");
        this.teardownFunctionsStatement = this.util.getSqlStatementFromFile("sql/functions/teardown_functions.sql");

        this.jdbcTemplate = new JdbcTemplate(this.dataSourceFactory.getDataSource());
    }

    @PostConstruct
    public void postConstruct() throws IOException, SQLException {
        buildDB();
    }

    private void buildDB() {
        jdbcTemplate.execute(setupStatement);
        jdbcTemplate.execute(setupFunctionsStatement);
    }

    public void resetDBDynamicData() {
        jdbcTemplate.execute(teardownDynamicTablesSql);

        buildDB();
    }
}
