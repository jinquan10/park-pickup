package org.jz.parkpickup;

import java.util.Set;

public interface ParkPickup {
    Set<PickupPlace> getPickupPlaces(double lat, double lng, int radiusMeters);

    void updateLocation(long playerId, double lat, double lng);

    void playToday(long playerId, long parkId, boolean isPlayingToday);

    void requestPickupGame(long playerId, long parkId);
}
