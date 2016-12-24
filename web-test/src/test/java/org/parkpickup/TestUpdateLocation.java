package org.parkpickup;

import org.junit.Test;
import org.parkpickup.client.ParkPickupV1Client;
import org.parkpickup.http.Location;
import org.parkpickup.http.exception.RequestFailedException;

import java.util.UUID;

public class TestUpdateLocation {
    private static final ParkPickupV1Client client = new ParkPickupV1Client();

    @Test
    public void test() throws RequestFailedException {
        String deviceId = UUID.randomUUID().toString();
        client.updateLocation(deviceId, new Location(47.667327, -122.147080));
    }
}
