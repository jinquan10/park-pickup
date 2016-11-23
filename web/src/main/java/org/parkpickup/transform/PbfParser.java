package org.parkpickup.transform;

import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.osm.Entity;
import net.morbz.osmonaut.osm.EntityType;
import net.morbz.osmonaut.osm.Tags;

/**
 * Created by JZ on 11/22/2016.
 */
public class PbfParser implements IOsmonautReceiver {
    @Override
    public boolean needsEntity(EntityType type, Tags tags) {
        return tags.hasKeyValue("leisure", "park");
    }

    @Override
    public void foundEntity(Entity entity) {
        System.out.println(entity.getTags().get("name") + ": " + entity.getCenter());
    }
}
