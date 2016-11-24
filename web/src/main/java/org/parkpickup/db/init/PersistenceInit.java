package org.parkpickup.db.init;

import org.parkpickup.Application;
import org.parkpickup.ResourceUtil;
import org.parkpickup.db.DataSourceFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.parkpickup.db.DataSourceFactory.adminUser;
import static org.parkpickup.db.DataSourceFactory.appDbName;

@Component
public class PersistenceInit {
    @Inject
    private ResourceUtil resourceUtil;

    @Inject
    private DataSourceFactory dataSourceFactory;

    @Inject
    private DbCreation dbCreation;

    public JdbcTemplate execute() throws FileNotFoundException, SQLException {
        JdbcTemplate appTemplate = dbCreation.initDb(appDbName, adminUser);

        String sqlStatement = resourceUtil.getSqlStatementFromFile("sql/setup.sql");
        appTemplate.execute(sqlStatement);

        return appTemplate;
    }

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);

        PersistenceInit persistenceInit = ctx.getBean(PersistenceInit.class);
        persistenceInit.execute();
    }
}
