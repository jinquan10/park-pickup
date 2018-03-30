package org.parkpickup;

import org.parkpickup.config.AppProp;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class Application {

    /**
     * Add to the @Value functionality by populating the Properties with
     * *.properties file
     *
     * This will combine both JVM and property file configs into @Value
     *
     * @return
     * @throws IOException
     */
    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() throws IOException {
        Properties properties = new AppProp().getPopulatedProperties();
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setProperties(properties);

        return propertyPlaceholderConfigurer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
