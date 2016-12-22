package org.parkpickup.etl;

import javax.inject.Inject;

import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.osm.Entity;
import net.morbz.osmonaut.osm.EntityType;
import net.morbz.osmonaut.osm.Relation;
import net.morbz.osmonaut.osm.Tags;
import net.morbz.osmonaut.osm.Way;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PbfReceiver implements IOsmonautReceiver {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private PbfElementProcessor pbfElementProcessor;

    @Override
    public boolean needsEntity(EntityType type, Tags tags) {
        return tags.hasKeyValue("leisure", "park");
    }

    /**
     * 1. way, leisure/park, closed
     * 3. relation, no leisure/park ways; use bounded
     * 4. relation, has leisure/park ways; put into separate table for manual processing
     *
     * @param entity
     */
    @Override
    public void foundEntity(Entity entity) {
        if (entity instanceof Way) {
            pbfElementProcessor.processWay((Way)entity);
        } else if (entity instanceof Relation) {
            pbfElementProcessor.processRelation((Relation)entity);
        }
    }
}