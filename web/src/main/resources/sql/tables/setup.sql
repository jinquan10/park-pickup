CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS park(
    id bigint PRIMARY KEY,
    name text,
    location geography(polygon,4326),
    location_center geography(point,4326),
    centerLat real,
    centerLng real
);

CREATE TABLE IF NOT EXISTS bad_relation(
    id bigint PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS bad_way(
    id bigint PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS person(
    id text PRIMARY KEY, -- the MAC Address
    name text,
    activities_text text
);

CREATE TABLE IF NOT EXISTS rel_person_park(
    device_id text PRIMARY KEY,
    park_id bigint,
    last_updated bigint
);

CREATE INDEX IF NOT EXISTS location_center_index ON park USING GIST (location_center);
CREATE INDEX IF NOT EXISTS location_index ON park USING GIST (location);
CREATE INDEX IF NOT EXISTS last_updated_index ON rel_person_park (last_updated);
CREATE INDEX IF NOT EXISTS activities_text_index ON person (activities_text);