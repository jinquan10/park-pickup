package org.parkpickup.db;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class CleanupOperationsDaoImpl extends BaseDao implements CleanupOperationsDao {
    private SimpleJdbcCall purgeIdleUsers;

    @PostConstruct
    public void postConstruct() {
        this.purgeIdleUsers = new SimpleJdbcCall(jdbcTemplate).withFunctionName("purge_idle_users");
    }

    @Override
    public void cleanupRelPersonPark(long ttlSeconds) {
        Map<String, Object> args = new HashMap<>();
        args.put("ttl", ttlSeconds);
        purgeIdleUsers.executeFunction(Void.class, args);
    }
}
