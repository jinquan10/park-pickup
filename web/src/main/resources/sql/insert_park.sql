INSERT INTO park (id, name, location)
VALUES (?, ?, ST_GeographyFromText(?))
ON CONFLICT(id)
DO UPDATE SET id=EXCLUDED.id, name=EXCLUDED.name, location=EXCLUDED.location;