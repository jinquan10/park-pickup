package net.morbz.osmonaut;

import net.morbz.osmonaut.osm.Entity;
import net.morbz.osmonaut.osm.EntityType;
import net.morbz.osmonaut.osm.Tags;

public class OsmonautTest {
    public static void main(String[] args) {
        // Set which OSM entities should be scanned (only nodes and ways in this case)
        EntityFilter filter = new EntityFilter(true, true, false);

        // Set the binary OSM source file
        Osmonaut naut = new Osmonaut("D:/pbf/washington-latest.osm.pbf", filter);

        // Start scanning by implementing the interface
        naut.scan(new IOsmonautReceiver() {
            @Override
            public boolean needsEntity(EntityType type, Tags tags) {
                return tags.hasKeyValue("leisure", "park");
            }

            @Override
            public void foundEntity(Entity entity) {
                System.out.println(entity.getTags().get("name") + ": " + entity.getCenter());
            }
        });
    }
}
