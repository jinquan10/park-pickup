package org.jz.integrationtest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jz.BaseTest;
import org.mockito.Mockito;
import org.parkpickup.api.PlayerName;
import org.parkpickup.api.exception.ApplicationException;
import org.parkpickup.api.exception.UserException;
import org.parkpickup.app.ParkPickupControllerV1Impl;
import org.parkpickup.db.ParkPickupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@DirtiesContext
public class ExceptionsIntegrationTest extends BaseTest {
    private ParkPickupDao parkPickupDao = Mockito.mock(ParkPickupDao.class);

    @Autowired
    private ParkPickupControllerV1Impl parkPickupControllerV1;

    @Before
    public void before() {
        try {
            ReflectionTestUtils.setField(this.parkPickupControllerV1, "parkPickupDao", this.parkPickupDao);
        } catch (Throwable e) {
            fail();
        }
    }

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
