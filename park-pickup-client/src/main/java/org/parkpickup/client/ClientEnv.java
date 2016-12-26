package org.parkpickup.client;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public enum ClientEnv {
    TEST("api", "localhost", 8080);

    private String protocol;
    private String domain;
    private int port;
    private static ClientEnv currentClientEnv;

    static {
        String envName = getJvmEnvParam();
        currentClientEnv = getClientEnv(envName);
    }

    private static String getJvmEnvParam() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();
        String env = null;
        for (String arg : arguments) {
            if (arg.contains("-Denv=")) {
                env = arg.substring(arg.indexOf("=") + 1);
                break;
            }
        }

        return env;
    }

    private static ClientEnv getClientEnv(String envName) {
        for (ClientEnv clientEnv : ClientEnv.values()) {
            if (clientEnv.name().equalsIgnoreCase(envName)) {
                return clientEnv;
            }
        }

        return null;// TODO return PROD env
    }

    ClientEnv(String protocol, String domain, int port) {
        this.protocol = protocol;
        this.domain = domain;
        this.port = port;
    }

    public static ClientEnv getCurrentClientEnv() {
        return currentClientEnv;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getPort() {
        return port;
    }

    public String getDomain() {
        return domain;
    }
}
