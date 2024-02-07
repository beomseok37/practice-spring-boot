CREATE TABLE IF NOT EXISTS board_user (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userId VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    nickname VARCHAR(45) NOT NULL,
    isAdmin TINYINT NOT NULL,
    isWithDraw TINYINT NOT NULL,
    createTime DATETIME NOT NULL,
    updateTime DATETIME NOT NULL,
    status VARCHAR(10) NOT NULL
)
