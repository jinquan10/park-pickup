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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        this.client.updateLocation(expectedDeviceId, grassLawnLocation);
        Collection<Park> parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, expectedActivities);

        assertEquals(1, parks.size());

        for (Park park : parks) {
            assertTrue(park.displayName.contains(expectedParkName));
            assertEquals(1, park.people.size());
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

    @Test
    public void noActivitiesSet_shouldStillReturnPeople() throws RequestFailedException {
        String expectedDeviceId = UUID.randomUUID().toString();
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        String expectedParkName = "Grass Lawn Park";

        this.client.updateLocation(expectedDeviceId, grassLawnLocation);
        Collection<Park> parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);

        assertEquals(1, parks.size());

        for (Park park : parks) {
            assertTrue(park.displayName.contains(expectedParkName));
            assertEquals(1, park.people.size());
            assertEquals(1, park.people.size());

            for (Person person : park.people) {
                assertEquals(expectedDeviceId, person.id);
                assertEquals(null, person.activities);
            }
        }
    }

    @Test
    public void activitiesSetTwice_shouldNotAffectOutcome() {

    }
}