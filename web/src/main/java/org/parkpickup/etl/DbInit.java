package org.parkpickup.etl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by JZ on 11/23/2016.
 */
public class DbInit {
    public void run(String sqlFilePath, String databaseName) throws FileNotFoundException, SQLException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(sqlFilePath).getFile());

        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            sb.append(line).append("\n");
        }

        scanner.close();

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/" + databaseName); // 5432 default postgres port
        ds.setUsername("postgres");
        ds.setPassword("parkpickup2016"); // TODO encrypt this

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        ResultSet resultSet = jdbcTemplate.getDataSource().getConnection().getMetaData().getCatalogs();

        while (resultSet.next()) {
            // Get the database name, which is at position 1
            System.out.println(resultSet.getString(1));
        }
        resultSet.close();
    }

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        DbInit dbInit = new DbInit();
        dbInit.run("sql/init_db.sql", "postgres");
        dbInit.run("sql/init_tables.sql", "parkpickup");
    }
}
