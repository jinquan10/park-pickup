package org.parkpickup;

import org.junit.Test;
import org.parkpickup.api.Location;
import org.parkpickup.api.exception.RequestFailedException;
import org.parkpickup.client.ParkPickupV1Client;

import java.util.UUID;

public class TestUpdateLocation {
    private static final ParkPickupV1Client client = new ParkPickupV1Client();

    @Test
    public void test() throws RequestFailedException {
        String deviceId = UUID.randomUUID().toString();
        client.updateLocation(deviceId, new Location(47.667327, -122.147080));
    }
}
