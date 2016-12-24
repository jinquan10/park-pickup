package org.parkpickup.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.parkpickup.http.Location;
import org.parkpickup.http.ParkPickupV1;
import org.parkpickup.http.exception.RequestFailedException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ParkPickupV1Client implements ParkPickupV1 {
    private static final ClientEnv clientEnv = ClientEnv.getCurrentClientEnv();

    @Override
    public void updateLocation(String deviceId, Location location) throws RequestFailedException {
        String path = updateLocationPath.replaceFirst("\\{deviceId\\}", deviceId);

        try {
            URL updateLocationUrl = new URL(clientEnv.getProtocol(), clientEnv.getDomain(), clientEnv.getPort(), path);

            HttpURLConnection httpUrlConnection = (HttpURLConnection) updateLocationUrl.openConnection();
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(httpUrlConnection.getOutputStream());
            out.write(new ObjectMapper().writeValueAsString(location));
            out.close();

            int responseCode = httpUrlConnection.getResponseCode();
            if (responseCode != 200) {
                throw new RequestFailedException();
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }
}
