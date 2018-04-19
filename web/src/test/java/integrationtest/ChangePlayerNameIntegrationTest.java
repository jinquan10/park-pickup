package integrationtest;

import org.junit.Test;
import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.Person;
import org.parkpickup.api.PlayerName;
import org.parkpickup.api.exception.ApplicationException;
import org.parkpickup.api.exception.UserException;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ChangePlayerNameIntegrationTest extends BaseIntegrationTest {
    @Test
    public void setPlayerNameFirstTime() throws ApplicationException, UserException {
        String deviceId = "deviceId";
        PlayerName playerName = new PlayerName("First Time Name");

        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        client.updateLocation(deviceId, grassLawnLocation);
        Collection<Park> parks = client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);

        for (Park park : parks) {
            for (Person person : park.people) {
                assertEquals(deviceId, person.id);
                assertNull(person.playerName);
            }
        }

        client.changePlayerName(deviceId, playerName);
        parks = client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);

        for (Park park : parks) {
            for (Person person : park.people) {
                assertEquals(deviceId, person.id);
                assertEquals(playerName.playerName, person.playerName);
            }
        }
    }

    @Test
    public void setPlayerNameAfterActivitiesSet() {

    }

    @Test
    public void changePlayerName() {

    }

    @Test
    public void nullOutPlayerName() {

    }

    @Test
    public void nameEmpty() {

    }

    @Test
    public void nameTooShort() {

    }

    @Test
    public void nameTooLong() {

    }
}
