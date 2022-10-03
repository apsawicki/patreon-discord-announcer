// h2 database (not mysql)

DROP TABLE IF EXISTS guilds;
create table guilds (
    guild VARCHAR(255) NOT NULL UNIQUE,
    channelid MEDIUMTEXT NOT NULL,
    guild_id INT PRIMARY KEY AUTO_INCREMENT,
    prefix VARCHAR(45)
);

DROP TABLE IF EXISTS urls;
create table urls (
    guild VARCHAR(255),
    url VARCHAR(255),
    url_id INT PRIMARY KEY AUTO_INCREMENT
);

DROP TABLE IF EXISTS posts;
create table posts (
    published VARCHAR(255),
    title VARCHAR(255),
    url VARCHAR(255),
    content MEDIUMTEXT,
    isprivate boolean,
    guild VARCHAR(255),
    post_id INT PRIMARY KEY AUTO_INCREMENT
);

