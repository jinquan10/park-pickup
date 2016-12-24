package org.parkpickup.db.init;

import org.parkpickup.Util;
import org.parkpickup.db.DataSourceFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.sql.SQLException;

@Component
public class DbCreation {
    @Inject
    private Util util;

    @Inject
    private DataSourceFactory dataSourceFactory;

    @PostConstruct
    public void initDb() throws SQLException, FileNotFoundException {
        boolean shouldInitDb = false;

        try {
            // - test the connection to db
            dataSourceFactory.getDataSource(DataSourceFactory.appDbName).getConnection();
        } catch (SQLException e) {
            if (e.getMessage().contains("does not exist")) {
                shouldInitDb = true;
            }
        }

        if (shouldInitDb) {
            String creationStatement = util.getSqlStatementFromFile("sql/init_db.sql");
            new JdbcTemplate(dataSourceFactory.getDataSource(DataSourceFactory.adminDb)).execute(creationStatement);
        }
    }
}
