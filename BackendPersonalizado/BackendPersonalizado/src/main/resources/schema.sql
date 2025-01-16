-- schema.sql
CREATE TABLE IF NOT EXISTS users (
id       INT  PRIMARY KEY AUTOINCREMENT NOT NULL,
email     VARCHAR      NOT NULL,
password     VARCHAR      NOT NULL,
role_id     INT      NOT NULL,
FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS roles (
id       INT  PRIMARY KEY AUTOINCREMENT NOT NULL,
name     VARCHAR      NOT NULL
);

CREATE TABLE IF NOT EXISTS activities (
id       INT  PRIMARY KEY AUTOINCREMENT NOT NULL,
name     VARCHAR      NOT NULL
);

CREATE TABLE IF NOT EXISTS participations (
id       INT  PRIMARY KEY AUTOINCREMENT NOT NULL,
user_id       INT      NOT NULL,
activity_id     INT      NOT NULL,
FOREIGN KEY (user_id) REFERENCES users(id),
FOREIGN KEY (activity_id) REFERENCES activities(id)
);