CREATE TABLE IF NOT EXISTS `users` (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    role VARCHAR(255),
    PRIMARY KEY (id)
);