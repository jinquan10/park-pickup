package org.parkpickup.http;

public interface ParkPickup {
    void updateLocation(String deviceId, Location location);


//    Set<PickupPlace> getPickupPlaces(double lat, double lng, int radiusMeters);
//    void playToday(long personId, long parkId, boolean isPlayingToday);
//    void requestPickupGame(long personId, long parkId);
}
