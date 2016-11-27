package org.parkpickup.db;

import org.parkpickup.ResourceUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SeedOperationsDao implements SeedOperations {
    private static final int batchSize = 500;
    private String insertSql;
    private List<Object[]> batchArgs = new ArrayList<>(batchSize);

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

        Object[] parkParams = new Object[3];
        parkParams[0] = id;
        parkParams[1] = name;
        parkParams[2] = String.format("SRID=4326;POINT(%s %s)", lng, lat);
        batchArgs.add(parkParams);

        if (batchArgs.size() == batchSize) {
            jdbcTemplate.batchUpdate(insertSql, batchArgs);
            batchArgs = new ArrayList<>(batchSize);
        }
    }

    @Override
    public void flush() {
        System.out.println("flushing batch insert parks");
        jdbcTemplate.batchUpdate(insertSql, batchArgs);
        batchArgs = new ArrayList<>(batchSize);
    }
}
