package org.parkpickup.db;

import org.parkpickup.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final int batchSize = 1000;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private String insertSql;
    private String insertBadRelationSql;
    private String insertBadWaySql;
    private List<Object[]> batchArgs = new ArrayList<>(batchSize);

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    private Util util;

    @PostConstruct
    public void postConstruct() throws FileNotFoundException, SQLException {
        insertSql = util.getSqlStatementFromFile("sql/insert_park.sql");
        insertBadRelationSql = util.getSqlStatementFromFile("sql/insert_bad_relation.sql");
        insertBadWaySql = util.getSqlStatementFromFile("sql/insert_bad_way.sql");
    }

    @Override
    public void addPark(Long id, String name, double[] lats, double[] lngs) {
        if (id == null) {
            LOGGER.warn("id is null.  park name: {}", name);
            return;
        }

        Object[] parkParams = new Object[3];
        parkParams[0] = id;
        parkParams[1] = name;
        parkParams[2] = util.createGeographyStringFromLatLng(lats, lngs);
        batchArgs.add(parkParams);

        if (batchArgs.size() == batchSize) {
            jdbcTemplate.batchUpdate(insertSql, batchArgs);
            batchArgs = new ArrayList<>(batchSize);
        }
    }

    @Override
    public void addBadRelation(Long id) {
        if (id == null) {
            LOGGER.warn("relation id is null");
            return;
        }

        jdbcTemplate.update(insertBadRelationSql, id);
    }

    @Override
    public void addBadWay(Long id) {
        if (id == null) {
            LOGGER.warn("way id is null");
            return;
        }

        jdbcTemplate.update(insertBadWaySql, id);
    }

    @Override
    public void flush() {
        System.out.println("flushing batch insert parks");
        jdbcTemplate.batchUpdate(insertSql, batchArgs);
        batchArgs = new ArrayList<>(batchSize);
    }
}
