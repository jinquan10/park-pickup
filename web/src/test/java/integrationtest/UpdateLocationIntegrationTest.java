package integrationtest;

import org.junit.Test;
import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.Person;
import org.parkpickup.api.exception.ClientRequestException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UpdateLocationIntegrationTest extends BaseIntegrationTest {
    @Test
    public void updateLocationTwice_ShouldHaveNoEffectOnReturnValue() throws
            ClientRequestException {
        String expectedDeviceId = randomUUID().toString();
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        String expectedParkName = "Grass Lawn Park";

        this.client.updateLocation(expectedDeviceId, grassLawnLocation);
        this.client.updateLocation(expectedDeviceId, grassLawnLocation);
        Collection<Park> parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);

        assertEquals(1, parks.size());

        for (Park park : parks) {
            assertTrue(park.displayName.contains(expectedParkName));
            verifyPark(expectedDeviceId, park);
        }
    }

    @Test
    public void multiplePeople_onePark_getsBothPeople() throws ClientRequestException {
        Set<String> people = new HashSet<>(Arrays.asList(new String[]{randomUUID().toString(), randomUUID().toString()}));
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        String expectedParkName = "Grass Lawn Park";
        int radiusMeters = 5000;

        for(String deviceId : people) {
            this.client.updateLocation(deviceId, grassLawnLocation);
        }

        Collection<Park> parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);
        assertEquals(1, parks.size());

        for (Park park : parks) {
            assertTrue(park.displayName.contains(expectedParkName));
            assertEquals(2, park.people.size());

            for (Person person : park.people) {
                people.remove(person.id);
                assertEquals(null, person.activities);
            }

            assertEquals(0, people.size());
        }
    }

    @Test
    public void multiplePeople_multipleParks_getsBothParks_withOnePersonEach() throws
            ClientRequestException {
        String personGrassLawn = randomUUID().toString();
        String personWelcomePark = randomUUID().toString();
        Location welcomeParkLocation = new Location(47.676511, -122.152171);
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        String grassLawnName = "Grass Lawn Park";
        String welcomeParkName = "Welcome Park";
        int radiusMeters = 5000;

        this.client.updateLocation(personGrassLawn, grassLawnLocation);
        this.client.updateLocation(personWelcomePark, welcomeParkLocation);

        Collection<Park> parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);
        assertEquals(2, parks.size());

        boolean verifiedGrassLawnPark = false;
        boolean verifiedWelcomePark = false;

        for (Park park : parks) {
            if (park.displayName.contains(grassLawnName)) {
                verifyPark(personGrassLawn, park);
                verifiedGrassLawnPark = true;
            } else if (park.displayName.contains(welcomeParkName)){
                verifyPark(personWelcomePark, park);
                verifiedWelcomePark = true;
            }
        }

        assertTrue(verifiedGrassLawnPark);
        assertTrue(verifiedWelcomePark);
    }

    private void verifyPark(String personId, Park park) {
        assertEquals(1, park.people.size());

        for (Person person : park.people) {
            assertEquals(personId, person.id);
            assertEquals(null, person.activities);
        }
    }
}
