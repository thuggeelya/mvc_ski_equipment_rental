create table if not exists users_table
(
    id       INTEGER primary key ,
    email    VARCHAR not null,
    password VARCHAR not null
);
INSERT INTO users_table(id,email,password) VALUES (01,'james@bond.com', 123);
INSERT INTO users_table(id,email,password) VALUES (11,'thuggeelya@mail.ru', 123);
INSERT INTO users_table(id,email,password) VALUES (21,'example@example.com', 123);
INSERT INTO users_table(id,email,password) VALUES (31,'root@mail.ru', 123);
INSERT INTO users_table(id,email,password) VALUES (41,'ilya@yandex.ru', 123);
create table if not exists person
(
    id       INTEGER primary key ,
    name     VARCHAR,
    lastName VARCHAR,
    age      INTEGER,
    phone    VARCHAR
);
INSERT INTO person(id,name,lastName,age,phone) VALUES (01,'James','Bond',65,'777777');
INSERT INTO person(id,name,lastName,age,phone) VALUES (11,'Ilya','Fedorov',21,'89609332438');
INSERT INTO person(id,name,lastName,age,phone) VALUES (21,'Example','Example',50,'505050');
INSERT INTO person(id,name,lastName,age,phone) VALUES (31,'Root','Adminson',30,'313233');
INSERT INTO person(id,name,lastName,age,phone) VALUES (41,'Ilya','Another',18,'89128340909');