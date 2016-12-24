package org.parkpickup.client;

public enum ClientEnv {
    TEST("test", "http", "localhost", "8080"),
    PROD

    private String env;
    private String protocol;
    private String domain;
    private String port;

    ClientEnv(String env, String protocol, String domain, String port) {
        this.env = env;
        this.protocol = protocol;
        this.domain = domain;
        this.port = port;
    }
}
