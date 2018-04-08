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

import java.util.Collection;
import java.util.UUID;

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
        String expectedDeviceId = UUID.randomUUID().toString();
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        String expectedParkName = "Grass Lawn Park";

        this.client.updateLocation(expectedDeviceId, grassLawnLocation);
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
}
