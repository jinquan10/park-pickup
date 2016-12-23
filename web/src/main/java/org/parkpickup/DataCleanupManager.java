package org.parkpickup;

import org.parkpickup.db.CleanupOperations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@DependsOn("persistenceInit")
public class DataCleanupManager {
    @Value("${idle.users.ttl.seconds}")
    private Long ttlSeconds;

    @Inject
    private CleanupOperations cleanupOperations;

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void postConstruct() {
        ttlSeconds /= 2;

        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                cleanupOperations.cleanupRelPersonPark();
            }
        }, 0, ttlSeconds, TimeUnit.SECONDS);
    }
}
