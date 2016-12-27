SELECT
  park.id,
  park.name,
  park.location,
  rel_person_park.device_id,
  person.name
FROM park
  INNER JOIN rel_person_park ON rel_person_park.park_id = park.id
  LEFT JOIN person ON person.id = rel_person_park.device_id
WHERE ST_DWithin(st_geographyFromText(?), park.location, ?);