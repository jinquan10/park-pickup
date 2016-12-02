DO $$ BEGIN
	PERFORM update_person_location(?,?,?);
END $$;
