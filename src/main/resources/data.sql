INSERT INTO theater (name, capacity)
SELECT 'Theater 1', 400 WHERE NOT EXISTS (SELECT 1 FROM theater WHERE name = 'Theater 1');

INSERT INTO theater (name, capacity)
SELECT 'Theater 2', 240 WHERE NOT EXISTS (SELECT 1 FROM theater WHERE name = 'Theater 2');

INSERT INTO users(user_id, date, email, password, phonenumber, username)
SELECT 1,'2002-11-6', 'TheGoat@gmail.com', 'Admin',
       12345678, 'August' WHERE NOT EXISTS (SELECT 1 FROM users WHERE user_id = 1);

INSERT INTO users(user_id, date, email, password, phonenumber, username)
SELECT 2,'1999-11-6', 'Misser@gmail.com', 'hej',
       87654321, 'Marco' WHERE NOT EXISTS (SELECT 2 FROM users WHERE user_id = 2);