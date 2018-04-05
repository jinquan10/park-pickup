package org.parkpickup.config;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class AppProp {
    private String env;

    /**
     * Get the env variable out of JVM args
     */
    public AppProp() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();

        for (String arg : arguments) {
            if (arg.contains("-Denv=")) {
                env = arg.substring(arg.indexOf("=") + 1);
                return;
            }
        }

        this.env = System.getProperty("env");

        if (env == null) {
            throw new RuntimeException("Need -Denv=");
        }
    }

    /**
     * Read from the properties file and populate the map of properties
     *
     * @throws IOException
     */
    public Properties getPopulatedProperties() throws IOException {
        ClassPathResource propertyFileResource = new ClassPathResource("config.properties");
        Scanner scanner = new Scanner(propertyFileResource.getFile());

        String envPrefix = env + "\\.";
        Properties properties = System.getProperties();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] firstFoo = line.split(envPrefix);

            if (firstFoo == null || firstFoo.length == 1) {
                continue;
            }

            String[] keyValue = firstFoo[1].split("=");
            properties.put(keyValue[0], keyValue[1]);
        }

        scanner.close();

        return properties;
    }
}
