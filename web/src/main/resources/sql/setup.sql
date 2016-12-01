CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS park(
    id bigint PRIMARY KEY,
    name text,
    location geography(linestring,4326)
);

CREATE TABLE IF NOT EXISTS person(
    id text PRIMARY KEY, -- the MAC Address
    name text
);

CREATE TABLE IF NOT EXISTS rel_person_park(
    person_id text,
    park_id bigint
);

CREATE INDEX IF NOT EXISTS location_index ON park USING GIST (location);