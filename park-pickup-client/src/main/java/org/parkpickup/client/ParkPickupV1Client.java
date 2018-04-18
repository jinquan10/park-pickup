package org.parkpickup.client;

import org.parkpickup.api.ActivityEnum;
import org.parkpickup.api.Location;
import org.parkpickup.api.Park;
import org.parkpickup.api.ParkPickupV1;
import org.parkpickup.api.exception.ApplicationException;
import org.parkpickup.api.exception.FailedRequest;
import org.parkpickup.api.exception.UserException;

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

    private interface Func {
        Object call() throws IOException;
    }

    @Override
    public void updateLocation(String deviceId, Location location) throws UserException, ApplicationException {
        this.doIt(() -> {
			doPut(deviceId, updateLocationPath, OBJECT_MAPPER.writeValueAsString(location));
			return null;
		});
    }

    @Override
    public Collection<Park> getParks(double lat, double lng, int radiusMeters, Set<ActivityEnum> activities) throws UserException, ApplicationException {
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

            sendAndGetResponse(httpUrlConnection, 200);

            String resultJsonString = Util.readFromInputStream(httpUrlConnection.getInputStream());
            Park[] parks = OBJECT_MAPPER.readValue(resultJsonString, Park[].class);
            return Arrays.asList(parks);
        } catch (Throwable e) {
            throw new UserException();
        }
    }

    @Override
    public void setActivities(String deviceId, Set<ActivityEnum> activities) throws UserException, ApplicationException {
        this.doIt(() -> {
            doPut(deviceId, setActivitiesPath, OBJECT_MAPPER.writeValueAsString(activities));
            return null;
        }
    }

    private void doPut(String deviceId, String setActivitiesPath, String body)
            throws IOException, ApplicationException, UserException {
        String path = setActivitiesPath.replaceFirst("\\{deviceId\\}", deviceId);

        URL url = new URL(clientEnv.getProtocol(), clientEnv.getDomain(), clientEnv.getPort(), path);
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setRequestProperty("Content-Type", "application/json");
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(httpUrlConnection.getOutputStream());
        out.write(body);
        out.close();

        sendAndGetResponse(httpUrlConnection, 200);
    }

    private void sendAndGetResponse(HttpURLConnection httpUrlConnection, int expectedCode)
            throws IOException, UserException, ApplicationException {
        int responseCode = httpUrlConnection.getResponseCode();

        if (responseCode != expectedCode) {
            if (responseCode == 400) {
                String resultJsonString = Util.readFromInputStream(httpUrlConnection.getInputStream());
                throw new UserException(OBJECT_MAPPER.readValue(resultJsonString, FailedRequest.class));
            } else if (responseCode == 500) {
                throw new ApplicationException(null);
            }
        }
    }

    private Object doIt(Func f) throws UserException, ApplicationException {
        try {
            return f.call();
        } catch (Throwable e) {
            if (e instanceof UserException) {
                throw (UserException)e;
            } else if (e instanceof ApplicationException) {
                throw (ApplicationException)e;
            } else {
                throw new ApplicationException(e);
            }
        }
    }
}
