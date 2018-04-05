package org.parkpickup.db.init;

import org.parkpickup.Util;
import org.parkpickup.db.DataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
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
    @Inject
    private Util util;

    @Inject
    private DataSourceFactory dataSourceFactory;

    @Value("${teardown.tables.all}")
    private boolean isTeardownAllTables;

    @Value("${teardown.tables.dynamic}")
    private boolean isTeardownDynamicTables;

    private final String setupStatement = util.getSqlStatementFromFile("sql/tables/setup.sql");
    private final String setupFunctionsStatement = util.getSqlStatementFromFile("sql/functions/setup_functions.sql");
    private final String teardownAllTablesSql = util.getSqlStatementFromFile("sql/tables/teardown_all.sql");
    private final String teardownDynamicTablesSql = util.getSqlStatementFromFile("sql/tables/teardown_dynamic.sql");
    private final String tearndownFunctionsStatement = util.getSqlStatementFromFile("sql/functions/teardown_functions.sql");

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceFactory.getDataSource());
    
    public PersistenceInit() throws IOException, SQLException {
    }

    @PostConstruct
    public void postConstruct() throws IOException, SQLException {

        if (isTeardownDynamicTables) {
            jdbcTemplate.execute(teardownDynamicTablesSql);

            if (isTeardownAllTables) {
                jdbcTemplate.execute(tearndownFunctionsStatement);
                jdbcTemplate.execute(teardownAllTablesSql);
            }
        }

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
