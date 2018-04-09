package integrationtest;

import org.junit.Before;
import org.junit.Test;
import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.Person;
import org.parkpickup.api.exception.RequestFailedException;
import org.parkpickup.db.CleanupOperationsDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PeopleLeftParkIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private CleanupOperationsDao cleanupOperationsDao;

    @Before
    public void before() {

    }

    @Test
    public void foo() throws RequestFailedException {
        String expectedDeviceId = randomUUID().toString();
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        String expectedParkName = "Grass Lawn Park";

        this.client.updateLocation(expectedDeviceId, grassLawnLocation);
        Collection<Park> parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);

        assertEquals(1, parks.size());

        for (Park park : parks) {
            assertTrue(park.displayName.contains(expectedParkName));
            verifyPark(expectedDeviceId, park);
        }

        this.cleanupOperationsDao.cleanupRelPersonPark();
    }

    private void verifyPark(String personId, Park park) {
        assertEquals(1, park.people.size());

        for (Person person : park.people) {
            assertEquals(personId, person.id);
            assertEquals(null, person.activities);
        }
    }
    // idle.users.ttl.seconds
}
