package org.parkpickup.db;

import org.parkpickup.api.Park;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParkPickupDaoImpl extends BaseDao implements ParkPickupDao {
    private SimpleJdbcCall updatePersonLocation;
    private String getPopulatedParksSql;

    @PostConstruct
    public void postConstruct() throws IOException, SQLException {
        this.updatePersonLocation = new SimpleJdbcCall(jdbcTemplate).withFunctionName("update_person_location");
        this.getPopulatedParksSql = util.getSqlStatementFromFile("sql/query/query_nearby_populated_parks.sql");
    }

    @Override
    public void updateLocation(String deviceId, double lat, double lng) {
        Map<String, Object> args = new HashMap<>();
        args.put("deviceId", deviceId);
        args.put("lat", lat);
        args.put("lng", lng);
        updatePersonLocation.executeFunction(Void.class, args);
    }

    @Override
    public List<Park> getPopulatedParks(double lat, double lng, int radiusMeters) {
        String point = String.format("SRID=4326;POINT(%s %s)", lng, lat);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(getPopulatedParksSql, new Object[]{point, radiusMeters});
        System.out.println("");

        return null;
    }
}
