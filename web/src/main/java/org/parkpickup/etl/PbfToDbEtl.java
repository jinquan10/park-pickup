package org.parkpickup.etl;

import net.morbz.osmonaut.EntityFilter;
import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.Osmonaut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by JZ on 11/22/2016.
 */
@Component
public class PbfToDbEtl {
    @Inject
    private IOsmonautReceiver osmonautReceiver;

    @Value("${pbfDir}")
    private String pbfDir;

    public void run() {
        EntityFilter filter = new EntityFilter(true, true, false);
        Osmonaut naut = new Osmonaut(pbfDir, filter);
        naut.scan(osmonautReceiver);
    }
}
