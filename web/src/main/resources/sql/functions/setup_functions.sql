CREATE OR REPLACE FUNCTION update_person_location(deviceId TEXT, lat REAL, lng REAL)
  RETURNS VOID AS $$

DECLARE withinParkId  BIGINT;
DECLARE currentParkId BIGINT;

BEGIN
  SELECT id
  INTO withinParkId
  FROM public.park
  WHERE ST_Dwithin(st_geographyFromText(format('SRID=4326;POINT(%s %s)', lng, lat)), location, 0, false);

  IF withinParkId IS NULL
  THEN
    DELETE FROM rel_person_park
    WHERE rel_person_park.device_id = deviceId;
  ELSE
    SELECT park_id
    INTO currentParkId
    FROM public.rel_person_park
    WHERE public.rel_person_park.device_id = deviceId;

    IF currentParkId IS NULL
    THEN
      INSERT INTO rel_person_park (device_id, park_id, last_updated)
      VALUES (deviceId, withinParkId, extract(EPOCH FROM now()));
    ELSE
      UPDATE
        rel_person_park
      SET
        park_id      = withinParkId,
        last_updated = extract(EPOCH FROM now())
      WHERE rel_person_park.device_id = deviceId;
    END IF;
  END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION purge_idle_users(ttl BIGINT)
  RETURNS VOID AS $$

DECLARE secondsAgo BIGINT;

BEGIN
  secondsAgo := (EXTRACT(EPOCH FROM now()) - ttl);

  DELETE FROM rel_person_park
  WHERE last_updated < secondsAgo;
END;
$$ LANGUAGE plpgsql;
