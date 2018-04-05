package org.parkpickup.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceFactory {
    public static final String adminUser = "postgres";
    public static final String adminDb = "postgres";
    public static final String adminPassword = "parkpickup2016";

    @Value("${db.name}")
    private String dbName;

    public DataSource getDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/" + this.dbName); // 5432 default postgres port
        ds.setUsername(this.adminUser);
        ds.setPassword(this.adminPassword);

        return ds;
    }

    public DataSource getEmptyDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/"); // 5432 default postgres port
        ds.setUsername(this.adminUser);
        ds.setPassword(this.adminPassword);

        return ds;
    }

    public String getDbName() {
        return this.dbName;
    }
}
