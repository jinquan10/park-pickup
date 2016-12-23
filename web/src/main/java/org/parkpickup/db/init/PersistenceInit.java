package org.parkpickup.db.init;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.parkpickup.Util;
import org.parkpickup.db.DataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@DependsOn("dbCreation")
public class PersistenceInit {
    @Inject
    private Util util;

    @Inject
    private DataSourceFactory dataSourceFactory;

    @Value("${teardown.tables}")
    private boolean isTeardownTables;

    @PostConstruct
    public void postConstruct() throws FileNotFoundException, SQLException {
        String setupStatement = util.getSqlStatementFromFile("sql/tables/setup.sql");
        String setupFunctionsStatement = util.getSqlStatementFromFile("sql/functions/setup_functions.sql");
        String teardownTablesStatement = util.getSqlStatementFromFile("sql/tables/teardown.sql");
        String tearndownFunctionsStatement = util.getSqlStatementFromFile("sql/functions/teardown_functions.sql");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceFactory.getDataSource(DataSourceFactory.appDbName));

        if (isTeardownTables) {
            jdbcTemplate.execute(teardownTablesStatement);
            jdbcTemplate.execute(tearndownFunctionsStatement);
        }

        jdbcTemplate.execute(setupStatement);
        jdbcTemplate.execute(setupFunctionsStatement);
    }
}
