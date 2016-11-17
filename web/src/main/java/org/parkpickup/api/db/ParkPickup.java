package org.parkpickup.api.db;

public interface ParkPickup {
    void updateDeviceLocation(long deviceId, double lat, double lng);
}
