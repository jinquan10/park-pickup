package org.parkpickup.etl;

import net.morbz.osmonaut.EntityFilter;
import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.Osmonaut;

import javax.inject.Inject;

/**
 * Created by JZ on 11/22/2016.
 */
public class PbfToDbEtl {
    @Inject
    private IOsmonautReceiver osmonautReceiver;

    public void run() {
        EntityFilter filter = new EntityFilter(true, true, false);
        Osmonaut naut = new Osmonaut("D:/pbf/washington-latest.osm.pbf", filter);
        naut.scan(osmonautReceiver);
    }

    public static void main(String[] args) {
        new PbfToDbEtl().run();
    }
}
