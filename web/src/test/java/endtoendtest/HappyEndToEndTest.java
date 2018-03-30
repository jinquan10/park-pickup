package endtoendtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.parkpickup.Application;
import org.parkpickup.client.ParkPickupV1Client;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = Application.class)
public class HappyEndToEndTest {
    private static final ParkPickupV1Client client = new ParkPickupV1Client();

    @Test
    public void setActivities_gotoGrassLawnPark_getNearbyParks500MetersAwayFromGrasslawn() {
        this.client.set
    }
}

