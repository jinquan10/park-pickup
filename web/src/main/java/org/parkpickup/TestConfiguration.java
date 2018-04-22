package org.parkpickup;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource("testConfig.properties")
@ComponentScan("org.parkpickup")
public class TestConfiguration {
}
