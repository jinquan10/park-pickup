package integrationtest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parkpickup.Application;
import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.Person;
import org.parkpickup.api.exception.RequestFailedException;
import org.parkpickup.client.ClientEnv;
import org.parkpickup.client.ParkPickupV1Client;
import org.parkpickup.db.init.PersistenceInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = Application.class)
public class UpdateLocationIntegrationTest {
    private static final ParkPickupV1Client client = new ParkPickupV1Client(ClientEnv.TEST);

    static {
        System.setProperty("env", "test");
    }

    @Autowired
    private PersistenceInit persistenceInit;

    @Before
    public void before() {
        this.persistenceInit.resetDBDynamicData();
    }

    @Test
    public void updateLocationTwice_ShouldHaveNoEffectOnReturnValue() throws RequestFailedException {
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
    public void multiplePeople_onePark_getsBothPeople() throws RequestFailedException {
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
    public void multiplePeople_multipleParks_getsBothParks_withOnePersonEach() throws RequestFailedException {
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
