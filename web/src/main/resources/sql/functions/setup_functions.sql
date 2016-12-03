CREATE OR REPLACE FUNCTION update_person_location(deviceId text, lat real, lng real) RETURNS void AS $$

DECLARE withinParkId bigint;

BEGIN
    SELECT id INTO withinParkId
    FROM public.park
    WHERE ST_within(ST_GeomFromText(format('POINT(%s %s)', lng, lat)), location);

    IF withinParkId IS NULL THEN
    	DELETE FROM rel_person_park
        WHERE rel_person_park.device_id = deviceId;
    ELSE
    	INSERT INTO rel_person_park(device_id, park_id)
        VALUES(deviceId, withinParkId)
        ON CONFLICT(device_id)
        DO NOTHING;
    END IF;
END;
$$ LANGUAGE plpgsql;