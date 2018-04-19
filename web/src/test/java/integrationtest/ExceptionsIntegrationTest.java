package integrationtest;

import org.junit.After;
import org.junit.Test;
import org.parkpickup.api.PlayerName;
import org.parkpickup.api.exception.ApplicationException;
import org.parkpickup.api.exception.UserException;
import org.parkpickup.db.ParkPickupDao;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ExceptionsIntegrationTest extends BaseIntegrationTest {
    @MockBean
    private ParkPickupDao parkPickupDao;

    @After
    public void after() {
        verifyNoMoreInteractions(this.parkPickupDao);
    }

    @Test
    public void testUserException() {
        String expectedDeviceId = "deviceId";
        String expectedPlayerName = "a";

        boolean hasCaughtException = false;
        try {
            client.changePlayerName(expectedDeviceId, new PlayerName(expectedPlayerName));
        } catch (UserException e) {
            hasCaughtException = true;
        } catch (ApplicationException e) {
        } finally {
            assertTrue(hasCaughtException);
        }
    }

    @Test
    public void testApplicationException() {
        when(this.parkPickupDao.getParks(anyDouble(), anyDouble(), anyInt(), any(Set.class)))
                .thenThrow(new RuntimeException());

        boolean hasCaughtException = false;
        try {
            client.getParks(1.d, 1.d, 1, null);
        } catch (UserException e) {
        } catch (ApplicationException e) {
            hasCaughtException = true;
        } finally {
            assertTrue(hasCaughtException);

            verify(this.parkPickupDao).getParks(anyDouble(), anyDouble(), anyInt(), any(Set.class));
        }
    }
}
