package org.parkpickup.db.init;

import org.parkpickup.ResourceUtil;
import org.parkpickup.db.DataSourceFactory;
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

    @Inject
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void postConstruct() throws FileNotFoundException, SQLException {
        String sqlStatement = resourceUtil.getSqlStatementFromFile("sql/setup.sql");
        jdbcTemplate.execute(sqlStatement);
    }
}
