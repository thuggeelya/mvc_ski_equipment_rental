DROP TABLE IF EXISTS users_table;

CREATE TABLE users_table(
    id INT PRIMARY KEY,
    email VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL
);