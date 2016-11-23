package org.parkpickup.transform;

import net.morbz.osmonaut.EntityFilter;
import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.Osmonaut;

import javax.inject.Inject;

/**
 * Created by JZ on 11/22/2016.
 */
public class PbfToDbTransformer {
    @Inject
    private IOsmonautReceiver osmonautReceiver;

    public void run() {
        // Set which OSM entities should be scanned (only nodes and ways in this case)
        EntityFilter filter = new EntityFilter(true, true, false);

        // Set the binary OSM source file
        Osmonaut naut = new Osmonaut("D:/pbf/washington-latest.osm.pbf", filter);

        // Start scanning by implementing the interface
        naut.scan(osmonautReceiver);
    }

    public static void main(String[] args) {
        new PbfToDbTransformer().run();
    }
}
