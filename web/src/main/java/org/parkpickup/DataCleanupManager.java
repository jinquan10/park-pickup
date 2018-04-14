package org.parkpickup;

import org.parkpickup.db.CleanupOperationsDao;
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
    @Value("${idle.users.refresh.seconds}")
    private long refreshRateSeconds;

    @Value("${idle.users.ttl.seconds}")
    private long ttlSeconds;

    @Inject
    private CleanupOperationsDao cleanupOperationsDao;

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void postConstruct() throws InterruptedException {
        this.reschedule(this.refreshRateSeconds * 1000L, this.ttlSeconds * 1000L);
    }

    public void reschedule(long refreshRateMillis, long ttlMillis) throws InterruptedException {
        this.executor.shutdown();
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                cleanupOperationsDao.cleanupRelPersonPark(ttlMillis / 1000L);
            }
        }, 0, refreshRateMillis, TimeUnit.MILLISECONDS);
    }
}
