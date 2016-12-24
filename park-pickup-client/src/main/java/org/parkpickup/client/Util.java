package org.parkpickup.client;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class Util {
    public void foo() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();

        for (String arg : arguments) {
            if (arg.contains("-Denv=")) {
                String env = arg.substring(arg.indexOf("=") + 1);
            }
        }
    }
}
