INSERT INTO bad_relation (id)
VALUES (?);
--ON CONFLICT(id)
--DO UPDATE SET id=EXCLUDED.id;