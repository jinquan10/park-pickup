-- Database: parkpickup

CREATE DATABASE %s
    WITH
    OWNER = %s
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

    -- TODO for testing CREATE DATABASE newdb WITH TEMPLATE originaldb OWNER dbuser;