package org.parkpickup.db;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ParkPickupDaoImpl extends BaseDao implements ParkPickupDao {
    private SimpleJdbcCall updatePersonLocation;

    @PostConstruct
    public void postConstruct() throws FileNotFoundException, SQLException {
        this.updatePersonLocation = new SimpleJdbcCall(jdbcTemplate).withFunctionName("update_person_location");
    }

    @Override
    public void updateLocation(String deviceId, double lat, double lng) {
        Map<String, Object> args = new HashMap<>();
        args.put("deviceId", deviceId);
        args.put("lat", lat);
        args.put("lng", lng);
        updatePersonLocation.executeFunction(Void.class, args);
    }
}
