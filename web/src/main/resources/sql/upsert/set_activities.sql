INSERT INTO person (id, activities_str)
VALUES (?, ?)
ON CONFLICT (id)
DO UPDATE SET (activities_str) = (?)
WHERE person.id = ?;