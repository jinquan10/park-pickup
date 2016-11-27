WITH result AS (
    SELECT
    	name,
    	st_distance(location, ST_GeographyFromText('SRID=4326;POINT(-122.14522 47.66774)')) as distance
	FROM
    	public.park
	WHERE
		name IS NOT NULL
	ORDER BY
    	location <-> ST_GeographyFromText('SRID=4326;POINT(-122.14522 47.66774)')
    LIMIT 1
)
SELECT
	name, distance
FROM
	result
WHERE
	distance < 100