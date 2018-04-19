INSERT INTO person (id, name)
VALUES (?, ?)
ON CONFLICT (id)
DO UPDATE SET name = ?
WHERE person.id = ?;