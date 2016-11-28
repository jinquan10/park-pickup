package org.parkpickup.db.init;

import org.parkpickup.ResourceUtil;
import org.parkpickup.db.DataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.sql.SQLException;

@Component
@DependsOn("dbCreation")
public class PersistenceInit {
    @Inject
    private ResourceUtil resourceUtil;

    @Inject
    private DataSourceFactory dataSourceFactory;

    @Value("${teardown}")
    private boolean isTeardown;

    @PostConstruct
    public void postConstruct() throws FileNotFoundException, SQLException {
        String setupStatement = resourceUtil.getSqlStatementFromFile("sql/setup.sql");
        String teardownStatement = resourceUtil.getSqlStatementFromFile("sql/teardown.sql");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceFactory.getDataSource(DataSourceFactory.appDbName));

        if (isTeardown) {
            jdbcTemplate.execute(teardownStatement);
        }

        jdbcTemplate.execute(setupStatement);
    }
}
