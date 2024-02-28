DROP TABLE IF EXISTS file;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post;
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

CREATE TABLE post
(
    id            BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(45)  NOT NULL,
    is_admin      BOOLEAN      NOT NULL,
    content       VARCHAR(200) NOT NULL,
    views         INT          NOT NULL,
    created_date  DATETIME     NOT NULL,
    modified_date DATETIME     NOT NULL,
    category_id   BIGINT       NOT NULL,
    user_id       BIGINT       NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE file
(
    id            BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name          varchar(45) NOT NULL,
    path          varchar(45) NOT NULL,
    extension     varchar(45) NOT NULL,
    post_id       BIGINT      NOT NULL,
    created_date  DATETIME    NOT NULL,
    modified_date DATETIME    NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post (id)
);

CREATE TABLE comment
(
    id        BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content   varchar(200) NOT NULL,
    parent_id BIGINT,
    post_id   BIGINT       NOT NULL,
    FOREIGN KEY (parent_id) REFERENCES comment (id),
    FOREIGN KEY (post_id) REFERENCES post (id)
);

CREATE TABLE tag
(
    id      BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    varchar(200) NOT NULL,
    url     varchar(200) NOT NULL,
    post_id BIGINT       NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post (id)
);