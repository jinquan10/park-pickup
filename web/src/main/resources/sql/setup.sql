CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS park(
    id bigint PRIMARY KEY,
    name text,
    location geometry(polygon)
);

CREATE TABLE IF NOT EXISTS bad_relation(
    id bigint PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS bad_way(
    id bigint PRIMARY KEY
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