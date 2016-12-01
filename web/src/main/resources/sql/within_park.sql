SELECT
	name
FROM
	public.park
WHERE
	ST_within(ST_GeomFromText('POINT(-122.69626 47.18754)'), location)