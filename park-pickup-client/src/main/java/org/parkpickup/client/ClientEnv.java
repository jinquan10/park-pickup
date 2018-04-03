package org.parkpickup.client;

public enum ClientEnv {
    TEST("http", "localhost", 8080);

    private String protocol;
    private String domain;
    private int port;

    ClientEnv(String protocol, String domain, int port) {
        this.protocol = protocol;
        this.domain = domain;
        this.port = port;
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
