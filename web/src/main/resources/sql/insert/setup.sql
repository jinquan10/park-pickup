DO $$ BEGIN
	PERFORM update_person_location('111', 47.667913, -122.146296);
END $$;

DO $$ BEGIN
	PERFORM update_person_location('222', 47.667913, -122.146296);
END $$;

INSERT INTO person VALUES('111', 'John');
INSERT INTO person VALUES('222', 'Bob');