package org.parkpickup.bo.impl;

import org.parkpickup.PickupPlace;
import org.parkpickup.api.ParkPickup;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ParkApiImpl implements ParkPickup {
    @Override
    public Set<PickupPlace> getPickupPlaces(double lat, double lng, int radiusMeters) {
        return null;
    }

    @Override
    public void updateLocation(long playerId, double lat, double lng) {

    }

    @Override
    public void playToday(long playerId, long parkId, boolean isPlayingToday) {

    }

    @Override
    public void requestPickupGame(long playerId, long parkId) {

    }
}
