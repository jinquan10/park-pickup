package org.parkpickup.api;

import org.parkpickup.api.exception.RequestFailedException;

import java.util.Collection;
import java.util.Set;

public interface ParkPickupV1 {
    String updateLocationPath = "/park-pickup/v1/location/{deviceId}";
    void updateLocation(String deviceId, Location location) throws RequestFailedException;

    String getPopulatedParksPath = "/park-pickup/v1/parks";
    Collection<Park> getPopulatedParks(double lat, double lng, int radiusMeters) throws RequestFailedException;

    String setActivitiesPath = "/park-pickup/v1/activities";
    void setActivities(String deviceId, Set<ActivityEnum> activities);

    //    void playToday(long personId, long parkId, boolean isPlayingToday);
//    void requestPickupGame(long personId, long parkId);
}
