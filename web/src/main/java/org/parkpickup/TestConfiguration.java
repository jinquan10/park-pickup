package org.parkpickup;

import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource("testConfig.properties")
@ComponentScan("org.parkpickup")
@Profile("test")
public class TestConfiguration {
}
