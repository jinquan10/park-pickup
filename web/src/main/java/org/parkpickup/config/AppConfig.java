package org.parkpickup.config;

import org.parkpickup.Application;
import org.parkpickup.etl.PbfToDbEtl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@Configuration
public class AppConfig {

    // TODO TTL for person at a park.
    // Should have a thread check TTL < x minutes so that it can clear the person from park
    // Update the TTL whenever we receive a person update

    // TODO Query for nearest parks within radius from lat/lng and return people who are there, if any
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
        PbfToDbEtl pbfToDbEtl = ctx.getBean(PbfToDbEtl.class);
        pbfToDbEtl.run();
    }
}
