package org.parkpickup.client;

import org.parkpickup.http.Location;
import org.parkpickup.http.ParkPickupV1;
import org.parkpickup.http.exception.RequestFailedException;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ParkPickupV1Client implements ParkPickupV1 {
    private static final ClientEnv clientEnv = ClientEnv.getCurrentClientEnv();

    @Override
    public void updateLocation(String deviceId, Location location) throws RequestFailedException {
        String path = UriBuilder.fromPath(updateLocationPath).build(deviceId).toASCIIString();

        try {
            URL updateLocationUrl = new URL(clientEnv.getProtocol(), clientEnv.getDomain(), clientEnv.getPort(), path);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) updateLocationUrl.openConnection();

            int responseCode = httpUrlConnection.getResponseCode();
            if (responseCode != 200) {
                throw new RequestFailedException();
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }
}
