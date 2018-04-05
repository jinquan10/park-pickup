package endtoendtest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parkpickup.Application;
import org.parkpickup.api.ActivityEnum;
import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.exception.RequestFailedException;
import org.parkpickup.client.ClientEnv;
import org.parkpickup.client.ParkPickupV1Client;
import org.parkpickup.db.init.PersistenceInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.parkpickup.api.ActivityEnum.BASKETBALL;
import static org.parkpickup.api.ActivityEnum.TENNIS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = Application.class)
@TestPropertySource(properties = {"env = test"})
public class HappyEndToEndTest {
    private static final ParkPickupV1Client client = new ParkPickupV1Client(ClientEnv.TEST);

    @Autowired
    private PersistenceInit persistenceInit;

    @Before
    public void before() {
        this.persistenceInit.resetDBDynamicData();
    }

    @Test
    public void setActivities_gotoGrassLawnPark_getNearbyParks500MetersAwayFromGrasslawn() throws RequestFailedException {
        String deviceId = UUID.randomUUID().toString();
        Set<ActivityEnum> activities = new HashSet<>(Arrays.asList(new ActivityEnum[]{TENNIS, BASKETBALL}));
        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        String expectedParkName = "Grass Lawn Park";

        this.client.setActivities(deviceId, activities);
        this.client.updateLocation(deviceId, grassLawnLocation);
        Collection<Park> parks = this.client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, activities);
    }
}