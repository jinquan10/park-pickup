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

    @PostConstruct
    public void postConstruct() throws IOException, SQLException {
        String setupStatement = util.getSqlStatementFromFile("sql/tables/setup.sql");
        String setupFunctionsStatement = util.getSqlStatementFromFile("sql/functions/setup_functions.sql");
        String teardownAllTablesSql = util.getSqlStatementFromFile("sql/tables/teardown_all.sql");
        String teardownDynamicTablesSql = util.getSqlStatementFromFile("sql/tables/teardown_dynamic.sql");
        String tearndownFunctionsStatement = util.getSqlStatementFromFile("sql/functions/teardown_functions.sql");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceFactory.getDataSource());

        if (isTeardownDynamicTables) {
            jdbcTemplate.execute(teardownDynamicTablesSql);
            jdbcTemplate.execute(tearndownFunctionsStatement);

            if (isTeardownAllTables) {
                jdbcTemplate.execute(teardownAllTablesSql);
            }
        }

        jdbcTemplate.execute(setupStatement);
        jdbcTemplate.execute(setupFunctionsStatement);
    }
}
