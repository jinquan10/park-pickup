package org.parkpickup.client;

import org.parkpickup.api.ActivityEnum;
import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.ParkPickupV1;
import org.parkpickup.api.exception.RequestFailedException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.parkpickup.client.Util.OBJECT_MAPPER;

public class ParkPickupV1Client implements ParkPickupV1 {
    private final ClientEnv clientEnv;

    public ParkPickupV1Client(ClientEnv clientEnv) {
        this.clientEnv = clientEnv;
    }

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
        } catch (Throwable e) {
            throw new RequestFailedException();
        }
    }

    private void sendRequest(HttpURLConnection httpUrlConnection, int expectedCode) throws IOException, RequestFailedException {
        int responseCode = httpUrlConnection.getResponseCode();
        if (responseCode != expectedCode) {
            throw new RequestFailedException();
        }
    }

    @Override
    public Collection<Park> getParks(double lat, double lng, int radiusMeters, Set<ActivityEnum> activities) throws RequestFailedException {
        try {
            String path = String.format(getParksPath + "?lat=%s&lng=%s&radiusMeters=%s", lat, lng, radiusMeters);

            StringBuilder sb = new StringBuilder();

            if (activities != null) {
                for (ActivityEnum activity : activities) {
                    sb.append("&activities=");
                    sb.append(activity);
                }
            }

            path += sb.toString();

            URL url = new URL(clientEnv.getProtocol(), clientEnv.getDomain(), clientEnv.getPort(), path);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setRequestProperty("Accept", "application/json");
            httpUrlConnection.setRequestMethod("GET");

            sendRequest(httpUrlConnection, 200);

            String resultJsonString = Util.readFromInputStream(httpUrlConnection.getInputStream());
            Park[] parks = OBJECT_MAPPER.readValue(resultJsonString, Park[].class);
            return Arrays.asList(parks);
        } catch (Throwable e) {
            throw new RequestFailedException();
        }
    }

    @Override
    public void setActivities(String deviceId, Set<ActivityEnum> activities) throws RequestFailedException {
        try {
            String path = setActivitiesPath.replaceFirst("\\{deviceId\\}", deviceId);

            URL url = new URL(clientEnv.getProtocol(), clientEnv.getDomain(), clientEnv.getPort(), path);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setRequestProperty("Content-Type", "application/json");
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(httpUrlConnection.getOutputStream());
            out.write(OBJECT_MAPPER.writeValueAsString(activities));
            out.close();

            sendRequest(httpUrlConnection, 200);
        } catch (Throwable e) {
            throw new RequestFailedException();
        }
    }
}
