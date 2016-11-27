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

    @PostConstruct
    public void postConstruct() throws FileNotFoundException, SQLException {
        String sqlStatement = resourceUtil.getSqlStatementFromFile("sql/setup.sql");
        new JdbcTemplate(dataSourceFactory.getDataSource(DataSourceFactory.appDbName)).execute(sqlStatement);
    }
}
