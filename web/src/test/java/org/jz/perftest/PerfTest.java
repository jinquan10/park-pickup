package org.jz.perftest;

import org.junit.Ignore;
import org.junit.Test;
import org.jz.BaseTest;
import org.parkpickup.api.Location;
import org.parkpickup.api.PlayerName;
import org.parkpickup.api.exception.ApplicationException;
import org.parkpickup.api.exception.UserException;

import java.util.UUID;

public class PerfTest extends BaseTest {
	@Ignore
	@Test
	public void testUpdateLocation() throws ApplicationException, UserException {
		String expectedDeviceId = UUID.randomUUID().toString();
		Location grassLawnLocation = new Location(47.667327, -122.147080);

		long numIterations = 100;
		long totalMillis = 0L;
		for (long i = 0; i < numIterations; i++) {
			long startMillis = System.currentTimeMillis();
//			client.updateLocation(expectedDeviceId, grassLawnLocation);
//			client.getParks(grassLawnLocation.lat, grassLawnLocation.lng, 5000, null);
			client.changePlayerName(expectedDeviceId, new PlayerName("playerName"));
			totalMillis += (System.currentTimeMillis() - startMillis);
		}

		System.out.println("Total millis: " + totalMillis);
		System.out.println("Average millis: " + totalMillis/numIterations);
	}
}
