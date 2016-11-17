package org.parkpickup.api.http;

import org.parkpickup.PickupPlace;

import java.util.Set;

public interface ParkPickup {
    void updateDeviceLocation(long deviceId, double lat, double lng);
    Set<PickupPlace> getPickupPlaces(double lat, double lng, int radiusMeters);




    void playToday(long deviceId, long parkId, boolean isPlayingToday);
    void requestPickupGame(long deviceId, long parkId);
}
