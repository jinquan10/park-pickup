package org.parkpickup.client;

import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.ParkPickupV1;
import org.parkpickup.api.exception.RequestFailedException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.parkpickup.client.Util.OBJECT_MAPPER;

public class ParkPickupV1Client implements ParkPickupV1 {
    private static final ClientEnv clientEnv = ClientEnv.getCurrentClientEnv();

    @Override
    public void updateLocation(String deviceId, Location location) throws RequestFailedException {
        String path = updateLocationPath.replaceFirst("\\{deviceId\\}", deviceId);

        try {
            URL updateLocationUrl = new URL(clientEnv.getProtocol(), clientEnv.getDomain(), clientEnv.getPort(), path);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) updateLocationUrl.openConnection();
            httpUrlConnection.setRequestProperty("Content-Type", "application/json");
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(httpUrlConnection.getOutputStream());
            out.write(OBJECT_MAPPER.writeValueAsString(location));
            out.close();

            sendRequest(httpUrlConnection, 200);
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }

    private void sendRequest(HttpURLConnection httpUrlConnection, int expectedCode) throws IOException, RequestFailedException {
        int responseCode = httpUrlConnection.getResponseCode();
        if (responseCode != expectedCode) {
            throw new RequestFailedException();
        }
    }

    @Override
    public List<Park> getPopulatedParks(double lat, double lng, int radiusMeters) throws RequestFailedException {
        try {
            String path = String.format(getPopulatedParksPath + "?lat=%s&lng=%s&radiusMeters=%s", lat, lng, radiusMeters);
            URL url = new URL(clientEnv.getProtocol(), clientEnv.getDomain(), clientEnv.getPort(), path);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setRequestProperty("Accept", "application/json");
            httpUrlConnection.setRequestMethod("GET");

            sendRequest(httpUrlConnection, 200);

            return Arrays.asList(OBJECT_MAPPER.readValue(Util.readFromInputStream(httpUrlConnection.getInputStream()), Park[].class));
        } catch (IOException e) {
        }

        return null;
    }
}
