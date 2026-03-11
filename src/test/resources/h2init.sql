CREATE TABLE IF NOT EXISTS users (
    user_id     INT PRIMARY KEY AUTO_INCREMENT,
     username    VARCHAR(55),
    phonenumber VARCHAR(55),
    email       VARCHAR(65),
    date        DATE,
    password    VARCHAR(55)
    );

INSERT INTO users (username, phonenumber, email, date, password)
VALUES ('Augist', '12345678', 'theGoat@email.com', '2000-01-01', 'Mr.ToSmooth');