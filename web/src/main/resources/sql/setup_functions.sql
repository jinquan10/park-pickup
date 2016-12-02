CREATE OR REPLACE FUNCTION update_person_location(personId text, lat real, lng real) RETURNS void AS $$

DECLARE withinParkId bigint;

BEGIN
    SELECT id INTO withinParkId
    FROM public.park
    WHERE ST_within(ST_GeomFromText(format('POINT(%s %s)', lng, lat)), location);

    IF withinParkId IS NULL THEN
    	DELETE FROM rel_person_park
        WHERE rel_person_park.person_id = personId;
    ELSE
    	INSERT INTO rel_person_park(person_id, park_id)
        VALUES(personId, withinParkId)
        ON CONFLICT(person_id)
        DO NOTHING;
    END IF;
END;
$$ LANGUAGE plpgsql;