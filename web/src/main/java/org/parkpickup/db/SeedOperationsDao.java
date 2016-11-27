package org.parkpickup.db;

import org.parkpickup.ResourceUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.sql.SQLException;

@Component
public class SeedOperationsDao implements SeedOperations {
    private String insertSql;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private ResourceUtil resourceUtil;

    @PostConstruct
    public void postConstruct() throws FileNotFoundException, SQLException {
        insertSql = resourceUtil.getSqlStatementFromFile("sql/insert_park.sql");
    }

    @Override
    public void addPark(Long id, String name, Double lat, Double lng) {
        System.out.println(String.format("addPark(): %s, %s, %s, %s", id, name, lat, lng));
        jdbcTemplate.update(insertSql, id, name, String.format("SRID=4326;POINT(%s %s)", lat, lng));
    }
}
