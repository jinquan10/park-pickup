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
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);
        PbfToDbEtl pbfToDbEtl = ctx.getBean(PbfToDbEtl.class);
        pbfToDbEtl.run();
    }
}
