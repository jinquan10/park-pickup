package org.parkpickup;

import org.junit.Test;
import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.Person;
import org.parkpickup.api.exception.RequestFailedException;
import org.parkpickup.client.ParkPickupV1Client;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

public class TestUpdateLocation {
    private static final ParkPickupV1Client client = new ParkPickupV1Client();

    @Test
    public void test() throws RequestFailedException {
        String deviceId = UUID.randomUUID().toString();
        Location location = new Location(47.667327, -122.147080);
        String containsParkName = "Grass";

        client.updateLocation(deviceId, location);
        List<Park> populatedParks = client.getPopulatedParks(location.lat, location.lng, 1500);
        assertNotNull(populatedParks);
        assertEquals(1, populatedParks.size());

        Park expectedPark = populatedParks.get(0);
        assertTrue(expectedPark.displayName.contains(containsParkName));

        Set<Person> playingPeople = expectedPark.playingNow;
        assertNotNull(playingPeople);
        assertEquals(1, playingPeople.size());

        for (Person person : playingPeople) {
            assertEquals(deviceId, person.id);
        }
    }
}
