package org.parkpickup.db;

import org.parkpickup.api.Park;

import java.util.Collection;

public interface ParkPickupDao {
    void updateLocation(String deviceId, double lat, double lng);
    Collection<Park> getPopulatedParks(double lat, double lng, int radiusMeters);
}
