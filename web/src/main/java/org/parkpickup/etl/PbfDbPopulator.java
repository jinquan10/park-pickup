package org.parkpickup.etl;

import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.osm.*;
import org.parkpickup.db.SeedOperations;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

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

        if (entity instanceof Relation) {
            Relation relation = (Relation)entity;

            List<RelationMember> members = relation.getMembers();

            boolean hasPark = false;
            boolean hasNo = false;
            for (RelationMember member : members) {
                if (member.getEntity().getTags().hasKeyValue("leisure", "park")) {
                    hasPark = true;
                }

                if (!member.getEntity().getTags().hasKeyValue("leisure", "park")) {
                    hasNo = true;
                }
            }

            if (hasPark && hasNo) {
                seedOperations.addPark(entity.getId(),
                        entity.getTags().get("name"),
                        latLon.getLat(),
                        latLon.getLon());
            }
        }
    }
}