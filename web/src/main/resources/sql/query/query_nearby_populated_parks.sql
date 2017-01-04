SELECT
  park.id,
  park.name as parkName,
  park.centerLat,
  park.centerLng,
  COUNT(park.id) as numPeople
FROM park
  INNER JOIN rel_person_park ON rel_person_park.park_id = park.id
WHERE ST_DWithin(st_geographyFromText(?), park.location_center, ?, false)
GROUP BY park.id
