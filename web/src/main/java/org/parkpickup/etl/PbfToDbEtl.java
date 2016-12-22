package org.parkpickup.etl;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import net.morbz.osmonaut.EntityFilter;
import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.Osmonaut;

import org.parkpickup.Util;
import org.parkpickup.db.DataSourceFactory;
import org.parkpickup.db.SeedOperationsDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@DependsOn("persistenceInit")
public class PbfToDbEtl {
    @Value("${pbfDir}")
    private String pbfDir;

    @Value("${teardown}")
    private boolean isTeardown;

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
        if (isTeardown) {
            run();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceFactory.getDataSource(DataSourceFactory.appDbName));
            String insertSetupStatement = util.getSqlStatementFromFile("sql/insert/setup.sql");
            jdbcTemplate.execute(insertSetupStatement);
        }
    }

    public void run() {
        EntityFilter filter = new EntityFilter(false, true, true);
        Osmonaut naut = new Osmonaut(pbfDir, filter);
        naut.scan(osmonautReceiver);
        seedOperationsDao.flush();

        // - process it again for relations that have leisure ways
        // delete the way ids if they exist and use the relation's boundary
    }
}
