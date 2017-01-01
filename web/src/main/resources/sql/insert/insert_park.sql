INSERT INTO park (id, name, location, location_center, centerLat, centerLng)
VALUES (?, ?, ST_GeographyFromText(?), ST_GeographyFromText(?), ?, ?);