package org.parkpickup.etl;

import net.morbz.osmonaut.EntityFilter;
import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.Osmonaut;
import org.parkpickup.Util;
import org.parkpickup.db.DataSourceFactory;
import org.parkpickup.db.SeedOperationsDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.sql.SQLException;

@Component
@DependsOn("persistenceInit")
public class PbfToDbEtl {
    @Value("${pbfSingleDir}")
    private String pbfSingleDir;

    @Value("${teardown.tables.all}")
    private boolean isTeardownAllTables;

    @Inject
    private IOsmonautReceiver osmonautReceiver;

    @Inject
    private SeedOperationsDao seedOperationsDao;

    @Inject
    private Util util;

    @Inject
    private DataSourceFactory dataSourceFactory;

    @PostConstruct
    public void postConstruct() throws FileNotFoundException, SQLException {
        if (isTeardownAllTables) {
            run();
        }
    }

    public void run() {
        EntityFilter filter = new EntityFilter(false, true, true);
        Osmonaut naut = new Osmonaut(pbfSingleDir, filter);
        naut.scan(osmonautReceiver);
        seedOperationsDao.flush();

        // - process it again for relations that have leisure ways
        // delete the way ids if they exist and use the relation's boundary
    }
}
