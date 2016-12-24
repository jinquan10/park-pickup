package org.parkpickup.db;

public interface ParkPickupDao {
    void updateLocation(String deviceId, double lat, double lng);
}
