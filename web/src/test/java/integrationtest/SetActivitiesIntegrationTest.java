package integrationtest;

import org.junit.Test;
import org.parkpickup.api.ActivityEnum;
import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.Person;
import org.parkpickup.api.exception.ClientRequestException;

import java.util.*;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.*;
import static org.parkpickup.api.ActivityEnum.BASKETBALL;
import static org.parkpickup.api.ActivityEnum.TENNIS;

public class SetActivitiesIntegrationTest extends BaseIntegrationTest {
    @Test
    public void setActivities_gotoGrassLawnPark_getNearbyParks500MetersAwayFromGrasslawn() throws
            ClientRequestException {
        String expectedDeviceId = UUID.randomUUID().toString();
        Set<ActivityEnum> expectedActivities = new HashSet<>(Arrays.asList(new ActivityEnum[]{TENNIS, BASKETBALL}));
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        String expectedParkName = "Grass Lawn Park";

        this.client.setActivities(expectedDeviceId, expectedActivities);
        setActivitiesValidation(expectedDeviceId, expectedActivities, grassLawnLocation, radiusMeters, expectedParkName);
    }

    @Test
    public void activitiesSetTwice_shouldNotAffectOutcome() throws ClientRequestException {
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
    public void activitiesNulled() throws ClientRequestException {
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
    public void noActivitiesSet_shouldStillReturnPeople() throws ClientRequestException {
        Set<String> people = new HashSet<>(Arrays.asList(new String[]{randomUUID().toString(), randomUUID().toString()}));
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        String expectedParkName = "Grass Lawn Park";

        for (String deviceId : people) {
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
    public void noActivitiesSet_getWithFilteredActivities_stillReturnPeople() throws
            ClientRequestException {
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

    @Test
    public void onePersonHasActivitiesSet_findWithFilter_returnsBothPeople() throws
            ClientRequestException {
        // - one person set activity basketball
        // - one person set nothing
        // - find with basketball
        // - should return both people

        String personBasketball = randomUUID().toString();
        String personNothingSet = randomUUID().toString();
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        String grassLawnName = "Grass Lawn Park";
        int radiusMeters = 5000;
        Set<ActivityEnum> basketballActivity = new HashSet<>(Arrays.asList(new ActivityEnum[]{BASKETBALL}));

        this.client.updateLocation(personBasketball, grassLawnLocation);
        this.client.updateLocation(personNothingSet, grassLawnLocation);

        this.client.setActivities(personBasketball, basketballActivity);

        // - Get both people
        Collection<Park> parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, basketballActivity);
        assertEquals(1, parks.size());

        for (Park park : parks) {
            assertEquals(grassLawnName, park.displayName);
            assertEquals(2, park.people.size());

            boolean activityBasketball = false;
            boolean activityNothing = false;
            for (Person person : park.people) {
                if (person.activities == null) {
                    activityNothing = true;
                } else if (person.activities.contains(BASKETBALL)) {
                    activityBasketball = true;
                }
            }

            assertTrue(activityBasketball);
            assertTrue(activityNothing);
        }
    }

    @Test
    public void multipleParks_multiplePeople_differentActivities() throws ClientRequestException {
        String personGrassLawn = randomUUID().toString();
        String personWelcomePark = randomUUID().toString();
        Location welcomeParkLocation = new Location(47.676511, -122.152171);
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        String grassLawnName = "Grass Lawn Park";
        String welcomeParkName = "Welcome Park";
        int radiusMeters = 5000;
        Set<ActivityEnum> tennisActivity = new HashSet<>(Arrays.asList(new ActivityEnum[]{TENNIS}));
        Set<ActivityEnum> basketballActivity = new HashSet<>(Arrays.asList(new ActivityEnum[]{BASKETBALL}));

        this.client.updateLocation(personGrassLawn, grassLawnLocation);
        this.client.updateLocation(personWelcomePark, welcomeParkLocation);

        this.client.setActivities(personGrassLawn, tennisActivity);
        this.client.setActivities(personWelcomePark, basketballActivity);

        // - Get both people
        Collection<Park> parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);
        assertEquals(2, parks.size());

        boolean verifiedGrassLawnPark = false;
        boolean verifiedWelcomePark = false;
        for (Park park : parks) {
            if (park.displayName.contains(grassLawnName)) {
                verifyPark(personGrassLawn, park, TENNIS);
                verifiedGrassLawnPark = true;
            } else if (park.displayName.contains(welcomeParkName)) {
                verifyPark(personWelcomePark, park, BASKETBALL);
                verifiedWelcomePark = true;
            }
        }
        assertTrue(verifiedGrassLawnPark);
        assertTrue(verifiedWelcomePark);

        parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, new HashSet<>(Arrays.asList(new ActivityEnum[]{TENNIS})));
        assertEquals(1, parks.size());
        for (Park park : parks) {
            verifyPark(personGrassLawn, park, TENNIS);
        }

        parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, new HashSet<>(Arrays.asList(new ActivityEnum[]{BASKETBALL})));
        assertEquals(1, parks.size());
        for (Park park : parks) {
            verifyPark(personWelcomePark, park, BASKETBALL);
        }
    }

    private void verifyPark(String personId, Park park, ActivityEnum activity) {
        assertEquals(1, park.people.size());

        for (Person person : park.people) {
            assertEquals(personId, person.id);
            assertTrue(person.activities.contains(activity));
        }
    }

    private void setActivitiesValidation(String expectedDeviceId, Set<ActivityEnum> expectedActivities, Location grassLawnLocation, int radiusMeters, String expectedParkName) throws
            ClientRequestException {
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