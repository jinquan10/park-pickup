package org.parkpickup.db;

public interface ParkPickup {
    void updateDeviceLocation(String personId, double lat, double lng);
}
