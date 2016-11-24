package org.parkpickup.etl;

import net.morbz.osmonaut.EntityFilter;
import net.morbz.osmonaut.IOsmonautReceiver;
import net.morbz.osmonaut.Osmonaut;
import org.parkpickup.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by JZ on 11/22/2016.
 */
@Component
public class PbfToDbEtl {
    @Inject
    private IOsmonautReceiver osmonautReceiver;

    public void run() {
        EntityFilter filter = new EntityFilter(true, true, false);
        Osmonaut naut = new Osmonaut("C:/pbf/washington-latest.osm.pbf", filter);
        naut.scan(osmonautReceiver);
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
        PbfToDbEtl pbfToDbEtl  = ctx.getBean(PbfToDbEtl.class);
        pbfToDbEtl.run();
    }
}
