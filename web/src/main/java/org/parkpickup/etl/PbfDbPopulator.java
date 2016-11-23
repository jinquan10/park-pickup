package org.parkpickup.etl;

import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.osm.Entity;
import net.morbz.osmonaut.osm.EntityType;
import net.morbz.osmonaut.osm.Tags;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Created by JZ on 11/22/2016.
 */
public class PbfDbPopulator implements IOsmonautReceiver {
    @Override
    public boolean needsEntity(EntityType type, Tags tags) {
        return tags.hasKeyValue("leisure", "park");
    }

    @Override
    public void foundEntity(Entity entity) {


        System.out.println(entity.getTags().get("name") + ": " + entity.getCenter());
    }

    public static void main(String[] args) {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/parkpickup"); // 5432 default postgres port
        ds.setUsername("postgres");
        ds.setPassword("parkpickup2016"); // TODO encrypt this

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

        try {
            jdbcTemplate.execute("DROP TABLE Persons");
        } catch (Throwable e) {
            System.out.println(e);
        }
    }
}