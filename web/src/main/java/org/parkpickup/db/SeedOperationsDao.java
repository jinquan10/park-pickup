package org.parkpickup.db;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Component
public class SeedOperationsDao implements SeedOperations {
    private SimpleJdbcInsert parkInsertTemplate;

    @Inject
    private DataSourceFactory dataSourceFactory;

    @PostConstruct
    public void posstConstruct() {
        parkInsertTemplate = new SimpleJdbcInsert(dataSourceFactory.getDataSource(DataSourceFactory.appDbName))
                                .withTableName("park");
    }

    @Override
    public void addPark(Long id, String name, Double lat, Double lng) {
        Map<String, Object> parkRow = new HashMap<>();
        parkRow.put("id", id);
        parkRow.put("name", name);
        parkRow.put("location", String.format("SetSRID(MakePoint(%s, %s), 4326)", lng, lat));

        parkInsertTemplate.execute(parkRow);
    }
}
