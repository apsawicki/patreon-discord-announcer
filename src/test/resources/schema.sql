
DROP TABLE IF EXISTS guilds;
create table guilds (
    guild VARCHAR(255) NOT NULL UNIQUE,
    channelid MEDIUMTEXT NOT NULL,
    guild_id INT PRIMARY KEY AUTO_INCREMENT,
    prefix VARCHAR(45)
);

DROP TABLE IF EXISTS urls;
create table urls (

);

DROP TABLE IF EXISTS posts;
create table posts (

);

