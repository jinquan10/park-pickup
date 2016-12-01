package org.parkpickup.etl;

import net.morbz.osmonaut.EntityFilter;
import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.Osmonaut;
import org.parkpickup.db.SeedOperationsDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class PbfToDbEtl {
    @Inject
    private IOsmonautReceiver osmonautReceiver;

    @Inject
    private SeedOperationsDao seedOperationsDao;

    @Value("${pbfDir}")
    private String pbfDir;

    public void run() {
        EntityFilter filter = new EntityFilter(false, true, true);
        Osmonaut naut = new Osmonaut(pbfDir, filter);
        naut.scan(osmonautReceiver);
        seedOperationsDao.flush();

        // - process it again for relations that have leisure ways
        // delete the way ids if they exist and use the relation's boundary
    }
}
