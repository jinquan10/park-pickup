package org.parkpickup.etl;

import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.osm.*;
import org.parkpickup.db.SeedOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by JZ on 11/22/2016.
 */
@Component
public class PbfDbPopulator implements IOsmonautReceiver {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private SeedOperations seedOperations;

    @Override
    public boolean needsEntity(EntityType type, Tags tags) {
        return tags.hasKeyValue("leisure", "park");
    }

    /**
     * 1. way, leisure/park, closed
     * 2. way, leisure/park, open, use bounded
     * 3. relation, no leisure/park ways; use bounded
     * 4. relation, has leisure/park ways; put into separate table for post processing
     * 5. post process 4: if way is within relation, remove way, use bounded relation
     * 6. post process 4: if way is not within relation, use bounded relation
     *
     * @param entity
     */
    @Override
    public void foundEntity(Entity entity) {
        LatLon latLon = entity.getCenter();

        if (entity instanceof Way) {
            Way way = (Way) entity;

            if (way.getNodes() == null) {
                LOGGER.warn("Way nodes is empty. way id: {}", way.getId());
                return;
            }

            int size = way.getNodes().
            way.getNodes()
        } else if (entity instanceof Relation) {

        }

        seedOperations.addPark(entity.getId(),
                entity.getTags().get("name"),
                latLon.getLat(),
                latLon.getLon());
    }
}