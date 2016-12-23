package org.parkpickup.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CleanupOperationsDao extends BaseDao implements CleanupOperations {
    @Value("${idle.users.ttl.seconds}")
    private Long ttlSeconds;

    private SimpleJdbcCall purgeIdleUsers;

    @PostConstruct
    public void postConstruct() throws FileNotFoundException, SQLException {
        this.purgeIdleUsers = new SimpleJdbcCall(jdbcTemplate).withFunctionName("purge_idle_users");
        ttlSeconds /= 2;
    }

    @Override
    public void cleanupRelPersonPark() {
        Map<String, Object> args = new HashMap<>();
        args.put("ttl", ttlSeconds);
        purgeIdleUsers.executeFunction(Void.class, args);
    }
}
