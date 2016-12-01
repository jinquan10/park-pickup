package org.parkpickup.etl;

import net.morbz.osmonaut.geometry.Bounds;
import net.morbz.osmonaut.osm.*;
import org.parkpickup.db.SeedOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

import static net.morbz.osmonaut.osm.EntityType.WAY;

@Component
public class PbfElementProcessor {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private SeedOperations seedOperations;

    public void processWay(Way way) {
        if (way.getNodes() == null) {
            LOGGER.warn("Way nodes is empty. way id: {}", way.getId());
            return;
        }

        List<Node> nodes = way.getNodes();
        int size = nodes.size();

        // - if we have a closed loop
        if (nodes.get(0).getId() == nodes.get(size - 1).getId()) {
            double[] lats = new double[size];
            double[] lngs = new double[size];

            for (int i = 0; i < size; i++) {
                LatLon latlon = nodes.get(i).getLatlon();
                lats[i] = latlon.getLat();
                lngs[i] = latlon.getLon();
            }

            seedOperations.addPark(way.getId(), way.getTags().get("name"), lats, lngs);
        } else {
            seedOperations.addBadWay(way.getId());
        }
    }

    public void processRelation(Relation relation) {
        if (relation.getMembers() == null) {
            LOGGER.warn("Relation members empty. relation id: {}", relation.getId());
            return;
        }

        List<RelationMember> members = relation.getMembers();
        for (RelationMember member : members) {
            Entity entity = member.getEntity();
            if (entity.getEntityType() == WAY) {
                Way way = (Way) entity;
                if (way.getTags().hasKeyValue("leisure", "park")) {
                    seedOperations.addBadRelation(relation.getId());
                    return;
                }
            }
        }

        Bounds bounds = relation.getBounds();
        double[] lats = new double[5];
        double[] lngs = new double[5];

        // - starting from top left clockwise
        lats[0] = bounds.getMinLat();
        lngs[0] = bounds.getMinLon();
        lats[1] = bounds.getMinLat();
        lngs[1] = bounds.getMaxLon();
        lats[2] = bounds.getMaxLat();
        lngs[2] = bounds.getMaxLon();
        lats[3] = bounds.getMaxLat();
        lngs[3] = bounds.getMinLon();
        lats[4] = lats[0];
        lngs[4] = lngs[0];

        seedOperations.addPark(relation.getId(), relation.getTags().get("name"), lats, lngs);
    }
}
