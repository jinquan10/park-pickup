package org.parkpickup.etl;

import net.morbz.osmonaut.EntityFilter;
import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.Osmonaut;
import org.parkpickup.Util;
import org.parkpickup.db.DataSourceFactory;
import org.parkpickup.db.SeedOperationsDao;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@DependsOn("persistenceInit")
public class PbfToDbEtl {
    @Inject
    private IOsmonautReceiver osmonautReceiver;

    @Inject
    private SeedOperationsDao seedOperationsDao;

    @Inject
    private Util util;

    @Inject
    private DataSourceFactory dataSourceFactory;

    public void run(String pbfSingleDir) {
        EntityFilter filter = new EntityFilter(false, true, true);
        Osmonaut naut = new Osmonaut(pbfSingleDir, filter);
        naut.scan(osmonautReceiver);
        seedOperationsDao.flush();

        // - process it again for relations that have leisure ways
        // delete the way ids if they exist and use the relation's boundary
    }
}
