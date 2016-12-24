package org.parkpickup.client;

import java.util.HashMap;
import java.util.Map;

public class ParkPickupDomainEnvMap {
    private static final Map<String, String> map = new HashMap<>();

    static {
        for (ClientEnv env : ClientEnv.values()) {
            map.put(env.)
        }
    }

    public static String getEnvDomain(String env) {
        return map.get(env);
    }
}
