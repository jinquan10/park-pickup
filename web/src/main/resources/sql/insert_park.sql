INSERT INTO park (id, name, location)
VALUES (?, ?, ST_GeographyFromText(?));