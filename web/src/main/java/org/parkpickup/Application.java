package org.parkpickup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

// TODO Populate db with pbf data
// TODO write sql query knn with radius search
// TODO measure timing to get nearest with radius
// TODO measure timing for nearest without radius