package org.parkpickup.etl;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceFactory {
    public DataSource getDataSource(String adminName, String password, String dbName) {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/" + dbName); // 5432 default postgres port
        ds.setUsername(adminName);
        ds.setPassword(password);

        return ds;
    }
}
