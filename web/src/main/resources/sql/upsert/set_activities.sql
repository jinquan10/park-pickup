INSERT INTO person (id, activities_text)
VALUES (?, ?)
ON CONFLICT (id)
DO UPDATE SET (activities_text) = (?)
WHERE person.id = ?;