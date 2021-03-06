package org.parkpickup.etl;

import net.morbz.osmonaut.EntityFilter;
import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.Osmonaut;
import org.parkpickup.Util;
import org.parkpickup.db.DataSourceFactory;
import org.parkpickup.db.SeedOperations;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@DependsOn("persistenceInit")
public class PbfToDbEtl {
    @Inject
    private IOsmonautReceiver osmonautReceiver;

    @Inject
    private SeedOperations seedOperationsDao;

    @Inject
    private Util util;

    @Inject
    private DataSourceFactory dataSourceFactory;

    public void loadSinglePbf(String pbfSingleDir) {
        EntityFilter filter = new EntityFilter(false, true, true);
        Osmonaut naut = new Osmonaut(pbfSingleDir, filter);
        naut.scan(osmonautReceiver);
        seedOperationsDao.flush();

        // - process it again for relations that have leisure ways
        // delete the way ids if they exist and use the relation's boundary
    }
}
