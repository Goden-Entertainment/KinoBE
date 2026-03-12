INSERT INTO theater (name, capacity)
SELECT 'Theater 1', 400 WHERE NOT EXISTS (SELECT 1 FROM theater WHERE name = 'Theater 1');

INSERT INTO theater (name, capacity)
SELECT 'Theater 2', 240 WHERE NOT EXISTS (SELECT 1 FROM theater WHERE name = 'Theater 2');