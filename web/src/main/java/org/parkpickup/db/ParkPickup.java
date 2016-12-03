package org.parkpickup.db;

public interface ParkPickup {
    void updateDeviceLocation(String deviceId, double lat, double lng);
}
