INSERT INTO theater (name, capacity)
SELECT 'Theater 1', 400 WHERE NOT EXISTS (SELECT 1 FROM theater WHERE name = 'Theater 1');

INSERT INTO theater (name, capacity)
SELECT 'Theater 2', 240 WHERE NOT EXISTS (SELECT 1 FROM theater WHERE name = 'Theater 2');

INSERT INTO movie (title, age_limit, duration, description, status, image)
SELECT 'The Matrix', 16, 136, 'A computer hacker learns the true nature of reality', 'ACTIVE', NULL
    WHERE NOT EXISTS (SELECT 1 FROM movie WHERE title = 'The Matrix');

INSERT INTO movie (title, age_limit, duration, description, status, image)
SELECT 'Inception', 12, 148, 'A thief who steals corporate secrets through dream-sharing technology', 'ACTIVE', NULL
    WHERE NOT EXISTS (SELECT 1 FROM movie WHERE title = 'Inception');

INSERT INTO movie (title, age_limit, duration, description, status, image)
SELECT 'The Dark Knight', 12, 152, 'Batman faces the Joker, a criminal mastermind', 'ACTIVE', NULL
    WHERE NOT EXISTS (SELECT 1 FROM movie WHERE title = 'The Dark Knight');

INSERT INTO movie (title, age_limit, duration, description, status, image)
SELECT 'Interstellar', 10, 169, 'A team of explorers travel through a wormhole in space', 'ACTIVE', NULL
    WHERE NOT EXISTS (SELECT 1 FROM movie WHERE title = 'Interstellar');

INSERT INTO movie (title, age_limit, duration, description, status, image)
SELECT 'The Shawshank Redemption', 15, 142, 'Two imprisoned men bond over years finding solace and redemption', 'ACTIVE', NULL
    WHERE NOT EXISTS (SELECT 1 FROM movie WHERE title = 'The Shawshank Redemption');

INSERT INTO movie (title, age_limit, duration, description, status, image)
SELECT 'Pulp Fiction', 18, 154, 'The lives of two mob hitmen intertwine in four tales of violence', 'ACTIVE', NULL
    WHERE NOT EXISTS (SELECT 1 FROM movie WHERE title = 'Pulp Fiction');

INSERT INTO movie (title, age_limit, duration, description, status, image)
SELECT 'Gladiator', 15, 155, 'A Roman general seeks revenge against the corrupt emperor', 'ACTIVE', NULL
    WHERE NOT EXISTS (SELECT 1 FROM movie WHERE title = 'Gladiator');

INSERT INTO movie (title, age_limit, duration, description, status, image)
SELECT 'The Lion King', 0, 88, 'A young lion prince flees his kingdom after his fathers murder', 'ACTIVE', NULL
    WHERE NOT EXISTS (SELECT 1 FROM movie WHERE title = 'The Lion King');

INSERT INTO movie (title, age_limit, duration, description, status, image)
SELECT 'Forrest Gump', 10, 142, 'The life journey of a slow-witted but kind-hearted man from Alabama', 'ACTIVE', NULL
    WHERE NOT EXISTS (SELECT 1 FROM movie WHERE title = 'Forrest Gump');

INSERT INTO movie (title, age_limit, duration, description, status, image)
SELECT 'Avengers: Endgame', 12, 181, 'The Avengers assemble once more to reverse Thanoss actions', 'ACTIVE', NULL
    WHERE NOT EXISTS (SELECT 1 FROM movie WHERE title = 'Avengers: Endgame');

INSERT INTO category (genre)
SELECT 'Action' WHERE NOT EXISTS (SELECT 1 FROM category WHERE genre = 'Action');

INSERT INTO category (genre)
SELECT 'Comedy' WHERE NOT EXISTS (SELECT 1 FROM category WHERE genre = 'Comedy');

INSERT INTO category (genre)
SELECT 'Drama' WHERE NOT EXISTS (SELECT 1 FROM category WHERE genre = 'Drama');

INSERT INTO category (genre)
SELECT 'Horror' WHERE NOT EXISTS (SELECT 1 FROM category WHERE genre = 'Horror');

INSERT INTO category (genre)
SELECT 'Thriller' WHERE NOT EXISTS (SELECT 1 FROM category WHERE genre = 'Thriller');

INSERT INTO category (genre)
SELECT 'Sci-Fi' WHERE NOT EXISTS (SELECT 1 FROM category WHERE genre = 'Sci-Fi');

INSERT INTO category (genre)
SELECT 'Romance' WHERE NOT EXISTS (SELECT 1 FROM category WHERE genre = 'Romance');

INSERT INTO category (genre)
SELECT 'Animation' WHERE NOT EXISTS (SELECT 1 FROM category WHERE genre = 'Animation');