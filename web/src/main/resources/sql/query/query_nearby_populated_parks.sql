SELECT
  park.id,
  park.name as parkName,
  park.centerLat,
  park.centerLng,
  person.id,
  person.name,
  person.activities_str
FROM park
  INNER JOIN rel_person_park ON rel_person_park.park_id = park.id
  INNER JOIN person ON person.id = rel_person_park.device_id
WHERE
    ST_DWithin(st_geographyFromText(?), park.location_center, ?, false)
GROUP BY
    park.id
