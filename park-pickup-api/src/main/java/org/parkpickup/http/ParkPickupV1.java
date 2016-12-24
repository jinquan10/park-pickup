package org.parkpickup.http;

import org.parkpickup.http.exception.RequestFailedException;

public interface ParkPickupV1 {
    String updateLocationPath = "/park-pickup/v1/location/{deviceId}";
    void updateLocation(String deviceId, Location location) throws RequestFailedException;


//    Set<PickupPlace> getPickupPlaces(double lat, double lng, int radiusMeters);
//    void playToday(long personId, long parkId, boolean isPlayingToday);
//    void requestPickupGame(long personId, long parkId);
}
