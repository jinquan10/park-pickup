package org.parkpickup.etl;

import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.osm.Entity;
import net.morbz.osmonaut.osm.EntityType;
import net.morbz.osmonaut.osm.Tags;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.File;

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
}
