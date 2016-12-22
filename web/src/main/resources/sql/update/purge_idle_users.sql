DO $$ BEGIN
  PERFORM purge_idle_users(?);
END $$;