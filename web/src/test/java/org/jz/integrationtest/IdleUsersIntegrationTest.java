package org.jz.integrationtest;

import org.junit.After;
import org.junit.Test;
import org.jz.BaseTest;
import org.parkpickup.DataCleanupManager;
import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.Person;
import org.parkpickup.api.exception.ApplicationException;
import org.parkpickup.api.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IdleUsersIntegrationTest extends BaseTest {
    @Autowired
    private DataCleanupManager dataCleanupManager;

    @Value("${idle.users.refresh.seconds}")
    private long refreshRateSeconds;

    @Value("${idle.users.ttl.seconds}")
    private long ttlSeconds;

    @After
    public void after() throws InterruptedException {
        this.dataCleanupManager.reschedule(this.refreshRateSeconds, this.ttlSeconds);
    }

    @Test
    public void visitPark_rescheduleTtl_0ParksReturned()
            throws UserException, InterruptedException, ApplicationException {
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

        this.dataCleanupManager.reschedule(1, 1);
        Thread.sleep(1000);
        parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);
        assertEquals(0, parks.size());
    }

    private void verifyPark(String personId, Park park) {
        assertEquals(1, park.people.size());

        for (Person person : park.people) {
            assertEquals(personId, person.id);
            assertEquals(null, person.activities);
        }
    }
}
