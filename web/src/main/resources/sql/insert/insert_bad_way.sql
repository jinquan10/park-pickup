INSERT INTO bad_way (id)
VALUES (?);
--ON CONFLICT(id)
--DO UPDATE SET id=EXCLUDED.id;