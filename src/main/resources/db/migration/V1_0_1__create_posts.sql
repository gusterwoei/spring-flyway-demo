CREATE TABLE IF NOT EXISTS `posts` (
    id INT NOT NULL AUTO_INCREMENT,
    userId INT NOT NULL,
    title VARCHAR(255),
    content VARCHAR(255),
    createdAt DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES users(id)
);