package main.java.org.jz.parkpickup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import static main.java.org.jz.parkpickup.Configuration.MAX_RADIUS_METERS;

@Component
public class ParkPickupImpl implements ParkPickup {
    @Autowired
    private Dao dao;

    @Override
    public Set<PickupPlace> getPickupPlaces(double lat, double lng, int radiusMeters) {
        if (radiusMeters > MAX_RADIUS_METERS) radiusMeters = MAX_RADIUS_METERS;

        return dao.getPickupPlaces(lat, lng, radiusMeters);
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
