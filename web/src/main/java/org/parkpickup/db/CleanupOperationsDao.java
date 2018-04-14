package org.parkpickup.db;

public interface CleanupOperationsDao {
    void cleanupRelPersonPark(long ttlSeconds);
}
