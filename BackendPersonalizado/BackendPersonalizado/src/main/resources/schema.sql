-- schema.sql
CREATE TABLE IF NOT EXISTS roles (
id       integer primary key auto_increment not null,
name     VARCHAR      NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
id       integer primary key auto_increment not null,
email     VARCHAR      NOT NULL,
password     VARCHAR      NOT NULL,
role_id INTEGER NOT NULL,
foreign key (role_id) references roles(id)
);

INSERT INTO roles(name) VALUES ('USER'), ('ADMIN');

CREATE TABLE IF NOT EXISTS activities (
id       integer primary key auto_increment not null,
name     VARCHAR      NOT NULL,
description VARCHAR NOT NULL,
place VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS participations(
id       integer primary key auto_increment not null,
user_id       INTEGER      NOT NULL,
activity_id     INTEGER     NOT NULL,
FOREIGN KEY (user_id) REFERENCES users(id),
FOREIGN KEY (activity_id) REFERENCES activities(id)
);