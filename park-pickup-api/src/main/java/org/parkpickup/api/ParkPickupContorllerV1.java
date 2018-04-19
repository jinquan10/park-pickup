package org.parkpickup.api;

import org.parkpickup.api.exception.ApplicationException;
import org.parkpickup.api.exception.UserException;

import java.util.Collection;
import java.util.Set;

public interface ParkPickupContorllerV1 {
    String updateLocationPath = "/park-pickup/v1/location/{deviceId}";
    void updateLocation(String deviceId, Location location) throws UserException, ApplicationException;

    String getParksPath = "/park-pickup/v1/parks";
    Collection<Park> getParks(double lat, double lng, int radiusMeters, Set<ActivityEnum> activities) throws UserException, ApplicationException;

    String setActivitiesPath = "/park-pickup/v1/activities/{deviceId}";
    void setActivities(String deviceId, Set<ActivityEnum> activities) throws UserException, ApplicationException;

    String playerNamePath = "/park-pickup/v1/player-name/{deviceId}";
    void changePlayerName(String deviceId, PlayerName playerName) throws UserException, ApplicationException;

    //    void playToday(long personId, long parkId, boolean isPlayingToday);
//    void requestPickupGame(long personId, long parkId);
}
