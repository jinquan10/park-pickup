package org.parkpickup.db;

import org.parkpickup.api.Park;

import java.util.List;

public interface ParkPickupDao {
    void updateLocation(String deviceId, double lat, double lng);
    List<Park> getPopulatedParks(double lat, double lng, int radiusMeters);
}
