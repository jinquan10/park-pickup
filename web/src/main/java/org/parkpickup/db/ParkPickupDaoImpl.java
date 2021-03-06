package org.parkpickup.db;

import org.parkpickup.api.ActivityEnum;
import org.parkpickup.api.Park;
import org.parkpickup.api.Person;
import org.parkpickup.domain.PersonAtAPark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class ParkPickupDaoImpl extends BaseDao implements ParkPickupDao {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private SimpleJdbcCall updatePersonLocation;
    private String getPopulatedParksSql;
    private String setActivitiesSql;
    private String changePlayerNameSql;

	@PostConstruct
    public void postConstruct() throws IOException, SQLException {
        this.updatePersonLocation = new SimpleJdbcCall(jdbcTemplate).withFunctionName("update_person_location");
        this.getPopulatedParksSql = util.getSqlStatementFromFile("sql/query/query_parks.sql");
        this.setActivitiesSql = util.getSqlStatementFromFile("sql/upsert/set_activities.sql");
        this.changePlayerNameSql = util.getSqlStatementFromFile("sql/upsert/change_player_name.sql");
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
    public Collection<Park> getParks(double lat, double lng, int radiusMeters, Set<ActivityEnum> activities) {
        StringBuilder whereClause = new StringBuilder();

        String point = String.format("SRID=4326;POINT(%s %s)", lng, lat);
        String withinRadius = String.format("ST_DWithin(st_geographyFromText('%s'), park.location_center, %s, false)", point, radiusMeters);

        whereClause.append(withinRadius);

        if (activities != null) {
            StringBuilder activitiesClause = new StringBuilder();
            activitiesClause.append("(activities_text ~ '^(");

            for (ActivityEnum activity : activities) {
                activitiesClause.append(activity);
                activitiesClause.append("|");
            }

            activitiesClause.replace(activitiesClause.length() - 1, activitiesClause.length(), "");
            activitiesClause.append(").*' OR activities_text IS NULL)");

            whereClause.append(" AND ");
            whereClause.append(activitiesClause.toString());
        }

        String queryString = String.format(this.getPopulatedParksSql, whereClause);

        List<PersonAtAPark> result = jdbcTemplate.query(queryString, new RowMapper<PersonAtAPark>() {
            @Override
            public PersonAtAPark mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new PersonAtAPark(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7));
            }
        });

        Map<Long, Park> parks = new HashMap<>();
        for (PersonAtAPark park : result) {
            long parkId = park.parkId;
            if (parks.containsKey(parkId)) {
                Set<Person> playingNow = parks.get(parkId).people;
                playingNow.add(new Person(park.deviceId, park.personName, park.activities));
            } else {
                Set<Person> playingNow = new HashSet<>();
                playingNow.add(new Person(park.deviceId, park.personName, park.activities));
                parks.put(park.parkId, new Park(park.parkId, park.centerLat, park.centerLng, park.parkName, playingNow));
            }
        }

        return parks.values();
    }

    @Override
    public void setActivities(String deviceId, Set<ActivityEnum> activities) {
        String activitiesString = null;

        if (activities != null) {
            StringBuilder sb = new StringBuilder();
            for (ActivityEnum activity : activities) {
                sb.append(activity);
                sb.append(",");
            }

            activitiesString = sb.toString();
        }

        upsert(this.setActivitiesSql, deviceId, activitiesString);
    }

	@Override
	public void changePlayerName(String deviceId, String name) {
		upsert(this.changePlayerNameSql, deviceId, name);
	}

	private void upsert(String sqlTemplate, String deviceId, String fieldValue) {
		int rowsAffected = jdbcTemplate.update(sqlTemplate, deviceId, fieldValue, fieldValue, deviceId);

		if (rowsAffected != 1) {
			LOGGER.error(String.format("Should have affected exactly 1 row, deviceId: %s", deviceId));
			throw new RuntimeException();
		}
	}
}
