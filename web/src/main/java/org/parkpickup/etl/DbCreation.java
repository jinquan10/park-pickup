package org.parkpickup.etl;

import org.parkpickup.ResourceUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DbCreation {
    @Inject
    private ResourceUtil resourceUtil;

    public void initDb(JdbcTemplate creationTemplate, String dbName, String owner) throws SQLException, FileNotFoundException {
        ResultSet resultSet = creationTemplate.getDataSource().getConnection().getMetaData().getCatalogs();

        while (resultSet.next()) {
            if (resultSet.getString(1).equals(dbName)) {
                String creationStatement = resourceUtil.getSqlStatementFromFile("sql/init_db.sql");
                String preparedCreationStatement = String.format(creationStatement, dbName, owner);
                creationTemplate.execute(preparedCreationStatement);
                break;
            }
        }
        resultSet.close();
    }
}
