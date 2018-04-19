package integrationtest;

import org.junit.Test;
import org.parkpickup.api.*;
import org.parkpickup.api.exception.ApplicationException;
import org.parkpickup.api.exception.UserException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.parkpickup.api.ActivityEnum.BASKETBALL;
import static org.parkpickup.api.ActivityEnum.TENNIS;
import static org.parkpickup.api.exception.FailedReason.VALIDATION;

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
    public void setPlayerNameInsert() throws ApplicationException, UserException {
        String deviceId = "deviceId";
        PlayerName playerName = new PlayerName("Inserted");

        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        client.changePlayerName(deviceId, playerName);
        client.updateLocation(deviceId, grassLawnLocation);
        Collection<Park> parks = client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);

        for (Park park : parks) {
            for (Person person : park.people) {
                assertEquals(deviceId, person.id);
                assertEquals(playerName.playerName, person.playerName);
            }
        }
    }

    @Test
    public void setPlayerNameAfterActivitiesSet() throws ApplicationException, UserException {
        String deviceId = "deviceId";
        PlayerName playerName = new PlayerName("Inserted");

        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;
        Set<ActivityEnum> expectedActivities = new HashSet<>(Arrays.asList(new ActivityEnum[]{TENNIS, BASKETBALL}));

        client.setActivities(deviceId, expectedActivities);
        client.changePlayerName(deviceId, playerName);
        client.updateLocation(deviceId, grassLawnLocation);
        Collection<Park> parks = client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);

        for (Park park : parks) {
            for (Person person : park.people) {
                assertEquals(deviceId, person.id);
                assertEquals(playerName.playerName, person.playerName);
            }
        }
    }

    @Test
    public void changePlayerName() throws ApplicationException, UserException {
        String deviceId = "deviceId";
        PlayerName playerName = new PlayerName("Inserted");

        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        client.updateLocation(deviceId, grassLawnLocation);
        client.changePlayerName(deviceId, playerName);
        Collection<Park> parks = client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);

        for (Park park : parks) {
            for (Person person : park.people) {
                assertEquals(deviceId, person.id);
                assertEquals(playerName.playerName, person.playerName);
            }
        }

        PlayerName newPlayerName = new PlayerName("New Player Name");
        client.changePlayerName(deviceId, newPlayerName);

        parks = client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);

        for (Park park : parks) {
            for (Person person : park.people) {
                assertEquals(deviceId, person.id);
                assertEquals(newPlayerName.playerName, person.playerName);
            }
        }
    }

    @Test
    public void nullOutPlayerName() throws ApplicationException, UserException {
        String deviceId = "deviceId";
        PlayerName playerName = new PlayerName("Inserted");

        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        client.updateLocation(deviceId, grassLawnLocation);
        client.changePlayerName(deviceId, playerName);
        Collection<Park> parks = client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);

        for (Park park : parks) {
            for (Person person : park.people) {
                assertEquals(deviceId, person.id);
                assertEquals(playerName.playerName, person.playerName);
            }
        }

        PlayerName newPlayerName = new PlayerName(null);
        client.changePlayerName(deviceId, newPlayerName);

        parks = client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);

        for (Park park : parks) {
            for (Person person : park.people) {
                assertEquals(deviceId, person.id);
                assertEquals(newPlayerName.playerName, person.playerName);
            }
        }
    }

    @Test
    public void nameEmpty() throws ApplicationException, UserException {
        testInvalidPlayerName("");
    }

    @Test
    public void nameTooShort() throws ApplicationException, UserException {
        testInvalidPlayerName("a");
    }

    @Test
    public void nameTooLong() throws ApplicationException, UserException {
        testInvalidPlayerName("1234567890123456789012345678901234567890");
    }

    private void testInvalidPlayerName(String s) throws UserException, ApplicationException {
        String deviceId = "deviceId";
        PlayerName playerName = new PlayerName("Inserted");

        Location grassLawnLocation = new Location(47.667327, -122.147080);
        int radiusMeters = 5000;

        client.updateLocation(deviceId, grassLawnLocation);
        client.changePlayerName(deviceId, playerName);
        Collection<Park> parks = client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, radiusMeters, null);

        for (Park park : parks) {
            for (Person person : park.people) {
                assertEquals(deviceId, person.id);
                assertEquals(playerName.playerName, person.playerName);
            }
        }

        PlayerName newPlayerName = new PlayerName(s);

        boolean caughtException = false;
        try {
            client.changePlayerName(deviceId, newPlayerName);
        } catch (UserException e) {
            assertEquals(e.failedRequest.failedReason, VALIDATION);
            caughtException = true;
        } finally {
            assertTrue(caughtException);
        }
    }
}
