package org.parkpickup.db;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceFactory {
    public static final String appDbName = "parkpickup";
    public static final String adminUser = "postgres";
    public static final String adminDb = "postgres";
    // TODO move to env
    public static final String adminPassword = "parkpickup2016";

    public DataSource getDataSource(String dbName) {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/" + dbName); // 5432 default postgres port
        ds.setUsername(adminUser);
        ds.setPassword(adminPassword);

        return ds;
    }
}
