DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS category;

CREATE TABLE user
(
    id            BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id       VARCHAR(45)  NOT NULL,
    password      VARCHAR(100) NOT NULL,
    nickname      VARCHAR(45)  NOT NULL,
    created_date  DATETIME     NOT NULL,
    modified_date DATETIME     NOT NULL,
    status        VARCHAR(10)  NOT NULL
);

CREATE TABLE category
(
    id            BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(45) NOT NULL,
    status        VARCHAR(10) NOT NULL,
    created_date  DATETIME    NOT NULL,
    modified_date DATETIME    NOT NULL
);