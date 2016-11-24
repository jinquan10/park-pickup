package org.parkpickup.db.init;

import org.parkpickup.ResourceUtil;
import org.parkpickup.db.DataSourceFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.parkpickup.db.DataSourceFactory.adminDb;
import static org.parkpickup.db.DataSourceFactory.appDbName;

@Component
public class DbCreation {
    @Inject
    private ResourceUtil resourceUtil;

    @Inject
    private DataSourceFactory dataSourceFactory;

    public JdbcTemplate initDb(String dbName, String owner) throws SQLException, FileNotFoundException {
        boolean shouldInitDb = false;

        try {
            // - test the connection to db
            dataSourceFactory.getDataSource(appDbName).getConnection();
        } catch (SQLException e) {
            if (e.getMessage().contains("does not exist")) {
                shouldInitDb = true;
            }
        }

        if (shouldInitDb) {
            String creationStatement = resourceUtil.getSqlStatementFromFile("sql/init_db.sql");
            new JdbcTemplate(dataSourceFactory.getDataSource(adminDb)).execute(creationStatement);
        }

        return new JdbcTemplate(dataSourceFactory.getDataSource(appDbName));
    }
}
