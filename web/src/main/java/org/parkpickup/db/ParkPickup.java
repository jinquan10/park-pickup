package org.parkpickup.db;

public interface ParkPickup {
    void updateDeviceLocation(long deviceId, double lat, double lng);
}
