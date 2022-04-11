DROP TABLE IF EXISTS person;

CREATE TABLE person(
    id INT PRIMARY KEY,
    name VARCHAR(250),
    lastName VARCHAR(250),
    age INT,
    phone VARCHAR(250)
);