package org.parkpickup.db;

import org.parkpickup.api.ActivityEnum;
import org.parkpickup.api.Park;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ParkPickupDao {
    void updateLocation(String deviceId, double lat, double lng);
    Collection<Park> getParks(double lat, double lng, int radiusMeters, List<ActivityEnum> activities);
    void setActivities(String deviceId, Set<ActivityEnum> activities);
}
