SELECT
  park.id,
  park.name as parkName,
  park.centerLat,
  park.centerLng,
  rel_person_park.device_id as deviceId,
  person.name as personName
FROM park
  INNER JOIN rel_person_park ON rel_person_park.park_id = park.id
  LEFT JOIN person ON person.id = rel_person_park.device_id
WHERE ST_DWithin(st_geographyFromText(?), park.location, ?);