package org.parkpickup.etl;

import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.osm.Entity;
import net.morbz.osmonaut.osm.EntityType;
import net.morbz.osmonaut.osm.LatLon;
import net.morbz.osmonaut.osm.Tags;
import org.parkpickup.db.SeedOperations;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by JZ on 11/22/2016.
 */
@Component
public class PbfDbPopulator implements IOsmonautReceiver {
    @Inject
    private SeedOperations seedOperations;

    @Override
    public boolean needsEntity(EntityType type, Tags tags) {
        return tags.hasKeyValue("leisure", "park");
    }

    @Override
    public void foundEntity(Entity entity) {
        LatLon latLon = entity.getCenter();

        seedOperations.addPark(entity.getId(),
                entity.getTags().get("name"),
                latLon.getLat(),
                latLon.getLon());
    }
}
