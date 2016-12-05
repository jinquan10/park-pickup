CREATE OR REPLACE FUNCTION update_person_location(deviceId text, lat real, lng real) RETURNS void AS $$

DECLARE withinParkId bigint;
DECLARE currentParkId bigint;

BEGIN
    SELECT id INTO withinParkId
    FROM public.park
    WHERE ST_within(ST_GeomFromText(format('POINT(%s %s)', lng, lat)), location);

    IF withinParkId IS NULL THEN
    	DELETE FROM rel_person_park
        WHERE rel_person_park.device_id = deviceId;
    ELSE
        SELECT park_id INTO currentParkId
        FROM public.rel_person_park
        WHERE public.rel_person_park.device_id = deviceId;

        IF currentParkId IS NULL THEN
            INSERT INTO rel_person_park(device_id, park_id)
            VALUES(deviceId, withinParkId);
        ELSE
            IF currentParkId != withinParkId THEN
                UPDATE rel_person_park
                SET park_id = withinParkId
                WHERE rel_person_park.device_id = deviceId;
            END IF;
        END IF;
    END IF;
END;
$$ LANGUAGE plpgsql;