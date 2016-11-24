package org.parkpickup.etl;

import org.parkpickup.ResourceUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.sql.SQLException;

@Component
public class PersistenceInit {
    public static final String appDbName = "parkpickup";
    public static final String adminUser = "postgres";
    public static final String adminDb = "postgres";
    public static final String adminPassword = "parkpickup2016";

    @Inject
    private ResourceUtil resourceUtil;

    @Inject
    private DataSourceFactory dataSourceFactory;

    @Inject
    private DbCreation dbCreation;

    public void execute() throws FileNotFoundException, SQLException {
        JdbcTemplate createDbTemplate = new JdbcTemplate(dataSourceFactory.getDataSource(adminUser, adminPassword, adminDb));
        dbCreation.initDb(createDbTemplate, appDbName, adminUser);

        JdbcTemplate initCreatedDbTemplate = new JdbcTemplate(dataSourceFactory.getDataSource(adminUser, adminPassword, appDbName));
        String sqlStatement = resourceUtil.getSqlStatementFromFile("sql/init_tables.sql");
        initCreatedDbTemplate.execute(sqlStatement);
    }

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        PersistenceInit persistenceInit = new PersistenceInit();
        persistenceInit.execute();
    }
}
