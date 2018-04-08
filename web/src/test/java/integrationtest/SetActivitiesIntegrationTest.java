package integrationtest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parkpickup.Application;
import org.parkpickup.api.ActivityEnum;
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

import java.util.*;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.*;
import static org.parkpickup.api.ActivityEnum.BASKETBALL;
import static org.parkpickup.api.ActivityEnum.TENNIS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = Application.class)
public class SetActivitiesIntegrationTest {
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
    public void setActivities_gotoGrassLawnPark_getNearbyParks500MetersAwayFromGrasslawn() throws RequestFailedException {
        String expectedDeviceId = UUID.randomUUID().toString();
        Set<ActivityEnum> expectedActivities = new HashSet<>(Arrays.asList(new ActivityEnum[]{TENNIS, BASKETBALL}));
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        String expectedParkName = "Grass Lawn Park";

        this.client.setActivities(expectedDeviceId, expectedActivities);
        setActivitiesValidation(expectedDeviceId, expectedActivities, grassLawnLocation, radiusMeters, expectedParkName);
    }

    @Test
    public void activitiesSetTwice_shouldNotAffectOutcome() throws RequestFailedException {
        String expectedDeviceId = UUID.randomUUID().toString();
        Set<ActivityEnum> expectedActivities = new HashSet<>(Arrays.asList(new ActivityEnum[]{TENNIS, BASKETBALL}));
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        String expectedParkName = "Grass Lawn Park";

        this.client.setActivities(expectedDeviceId, expectedActivities);
        this.client.setActivities(expectedDeviceId, expectedActivities);
        setActivitiesValidation(expectedDeviceId, expectedActivities, grassLawnLocation, radiusMeters, expectedParkName);
    }

    @Test
    public void activitiesNulled() throws RequestFailedException {
        String expectedDeviceId = UUID.randomUUID().toString();
        Set<ActivityEnum> expectedActivities = new HashSet<>(Arrays.asList(new ActivityEnum[]{TENNIS, BASKETBALL}));
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        String expectedParkName = "Grass Lawn Park";

        this.client.setActivities(expectedDeviceId, expectedActivities);
        setActivitiesValidation(expectedDeviceId, expectedActivities, grassLawnLocation, radiusMeters, expectedParkName);

        // - Null out activities
        this.client.setActivities(expectedDeviceId, null);
        Collection<Park> parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, expectedActivities);
        assertEquals(1, parks.size());

        for (Park park : parks) {
            assertTrue(park.displayName.contains(expectedParkName));
            assertEquals(1, park.people.size());

            for (Person person : park.people) {
                assertEquals(expectedDeviceId, person.id);
                assertEquals(null, person.activities);
            }
        }
    }

    @Test
    public void noActivitiesSet_shouldStillReturnPeople() throws RequestFailedException {
        Set<String> people = new HashSet<>(Arrays.asList(new String[]{randomUUID().toString(), randomUUID().toString()}));
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        String expectedParkName = "Grass Lawn Park";

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
    public void noActivitiesSet_getWithFilteredActivities_stillReturnPeople() throws RequestFailedException {
        String expectedDeviceId = UUID.randomUUID().toString();
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;
        String expectedParkName = "Grass Lawn Park";

        Set<ActivityEnum> expectedActivities = new HashSet<>(Arrays.asList(new ActivityEnum[]{TENNIS, BASKETBALL}));

        this.client.updateLocation(expectedDeviceId, grassLawnLocation);
        Collection<Park> parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, expectedActivities);
        assertEquals(1, parks.size());

        for (Park park : parks) {
            assertTrue(park.displayName.contains(expectedParkName));
            assertEquals(1, park.people.size());

            for (Person person : park.people) {
                assertEquals(expectedDeviceId, person.id);
                assertNull(person.activities);
            }
        }
    }

    private void setActivitiesValidation(String expectedDeviceId, Set<ActivityEnum> expectedActivities, Location grassLawnLocation, int radiusMeters, String expectedParkName) throws RequestFailedException {
        this.client.updateLocation(expectedDeviceId, grassLawnLocation);
        Collection<Park> parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, expectedActivities);

        assertEquals(1, parks.size());

        for (Park park : parks) {
            assertTrue(park.displayName.contains(expectedParkName));
            assertEquals(1, park.people.size());

            for (Person person : park.people) {
                assertEquals(expectedDeviceId, person.id);
                assertEquals(2, person.activities.size());

                for (ActivityEnum activity : person.activities) {
                    assertTrue(expectedActivities.contains(activity));
                }
            }
        }
    }
}