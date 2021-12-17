DROP TABLE IF EXISTS equipment;

CREATE TABLE equipment(
    id INT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    firm_name VARCHAR(250) NOT NULL,
    cost VARCHAR(250) NOT NULL,
    owner VARCHAR(250),
    description VARCHAR(250),
    is_fave BIT,
    available BIT,
    available_left INT
);