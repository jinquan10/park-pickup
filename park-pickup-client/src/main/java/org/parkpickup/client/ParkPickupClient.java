package org.parkpickup.client;

import org.parkpickup.http.Location;
import org.parkpickup.http.ParkPickup;

import java.net.HttpURLConnection;

public class ParkPickupClient implements ParkPickup {
    private HttpURLConnection httpURLConnection;

    @Override
    public void updateLocation(String deviceId, Location location) {

    }
}
