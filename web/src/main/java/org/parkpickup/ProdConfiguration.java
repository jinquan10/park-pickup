package org.parkpickup;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("config.properties")
@Profile("prod")
public class ProdConfiguration {
}
