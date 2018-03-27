package org.parkpickup.db;

import org.parkpickup.api.Park;
import org.parkpickup.api.Person;
import org.parkpickup.domain.NearbyPopulatedParks;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class ParkPickupDaoImpl extends BaseDao implements ParkPickupDao {
    private SimpleJdbcCall updatePersonLocation;
    private String getPopulatedParksSql;
    private String setActivitiesSql;

    @PostConstruct
    public void postConstruct() throws IOException, SQLException {
        this.updatePersonLocation = new SimpleJdbcCall(jdbcTemplate).withFunctionName("update_person_location");
        this.getPopulatedParksSql = util.getSqlStatementFromFile("sql/query/query_nearby_populated_parks.sql");
        this.setActivitiesSql = util.getSqlStatementFromFile("sql/update/set_activities.sql");
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
    public Collection<Park> getPopulatedParks(double lat, double lng, int radiusMeters) {
        String point = String.format("SRID=4326;POINT(%s %s)", lng, lat);

        List<NearbyPopulatedParks> result = jdbcTemplate.query(getPopulatedParksSql, new Object[]{point, radiusMeters}, new RowMapper<NearbyPopulatedParks>() {
            @Override
            public NearbyPopulatedParks mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new NearbyPopulatedParks(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6));
            }
        });

        Map<Long, Park> parks = new HashMap<>();
        for (NearbyPopulatedParks park : result) {
            long parkId = park.parkId;
            if (parks.containsKey(parkId)) {
                Set<Person> playingNow = parks.get(parkId).playingNow;
                playingNow.add(new Person(park.deviceId, park.personName));
            } else {
                Set<Person> playingNow = new HashSet<>();
                playingNow.add(new Person(park.deviceId, park.personName));
                parks.put(park.parkId, new Park(park.parkId, park.centerLat, park.centerLng, park.parkName, playingNow));
            }
        }

        return parks.values();
    }

    @Override
    public void setActivities(String deviceId, Set<String> activities) {
        jdbcTemplate.update(this.setActivitiesSql, activities);
    }
}
