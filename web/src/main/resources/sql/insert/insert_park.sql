INSERT INTO park (id, name, location, centerLat, centerLng)
VALUES (?, ?, ST_GeomFromText(?), ?, ?);