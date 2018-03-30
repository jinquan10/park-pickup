package org.parkpickup.db;

import org.parkpickup.api.ActivityEnum;
import org.parkpickup.api.Park;

import java.util.Collection;
import java.util.Set;

public interface ParkPickupDao {
    void updateLocation(String deviceId, double lat, double lng);
    Collection<Park> getPopulatedParks(double lat, double lng, int radiusMeters);
    void setActivities(String deviceId, Set<ActivityEnum> activities);
}
