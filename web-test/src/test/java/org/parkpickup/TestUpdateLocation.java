package org.parkpickup;

import org.junit.Test;
import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.Person;
import org.parkpickup.api.exception.ClientRequestException;
import org.parkpickup.client.ParkPickupV1Client;

import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestUpdateLocation {
    private static final ParkPickupV1Client client = new ParkPickupV1Client();

    @Test
    public void populate100() throws ClientRequestException {
        for (int i = 0; i < 100; i++) {
            String deviceId = UUID.randomUUID().toString();
            Location location = new Location(47.667327, -122.147080);

            client.updateLocation(deviceId, location);
        }
    }

    @Test
    public void test() throws ClientRequestException {
        String deviceId = UUID.randomUUID().toString();
        Location location = new Location(47.667327, -122.147080);
        String containsParkName = "Grass";

        client.updateLocation(deviceId, location);
        Collection<Park> populatedParks = client.getParks(location.lat, location.lng, 80450);

        assertNotNull(populatedParks);

        Park populatedPark = getExpectedPark(containsParkName, populatedParks);
        assertNotNull(populatedPark);

        boolean foundPerson = findPersonInPark(deviceId, populatedPark);
        assertTrue(foundPerson);
    }

    private boolean findPersonInPark(String deviceId, Park populatedPark) {
        boolean foundPerson = false;
        for (Person person : populatedPark.people) {
            if (deviceId.equals(person.id)) {
                foundPerson = true;
            }
        }
        return foundPerson;
    }

    private Park getExpectedPark(String containsParkName, Collection<Park> populatedParks) {
        Park populatedPark = null;
        for (Park park : populatedParks) {
            if (park.displayName.contains(containsParkName)) {
                populatedPark = park;
                break;
            }
        }
        return populatedPark;
    }
}
