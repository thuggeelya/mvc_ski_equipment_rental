create table if not exists equipment
(
    id             INTEGER primary key ,
    name           VARCHAR not null,
    firm_name      VARCHAR not null,
    cost           INTEGER not null,
    owner          VARCHAR,
    description    VARCHAR,
    available      BOOLEAN,
    available_left INTEGER
);
INSERT INTO equipment(id,name,firm_name,cost,description,available,available_left) VALUES (123,'Ski Pro','Atom',350,'High quality',true,100);
INSERT INTO equipment(id,name,firm_name,cost,description,available,available_left) VALUES (234,'Ski Bounce','IDK',300,'Good quality',true,600);
INSERT INTO equipment(id,name,firm_name,cost,description,available,available_left) VALUES (345,'Snowboard Beepo','Smth',400,'Average quality',true,200);
INSERT INTO equipment(id,name,firm_name,cost,description,available,available_left) VALUES (456,'Sticks Pro','Atom',50,'Sticks to "Ski Pro"',true,100);
INSERT INTO equipment(id,name,firm_name,cost,description,available,available_left) VALUES (567,'Skates Glitters','Burton',300,'Glitter on ice',true,100);
create table if not exists users_equipment
(
    id    INTEGER primary key,
    u_id  INTEGER not null,
    eq_id VARCHAR not null
);
INSERT INTO users_equipment(id,u_id,eq_id) VALUES (01,01,123);
INSERT INTO users_equipment(id,u_id,eq_id) VALUES (11,11,234);
INSERT INTO users_equipment(id,u_id,eq_id) VALUES (21,21,345);
INSERT INTO users_equipment(id,u_id,eq_id) VALUES (31,31,456);
INSERT INTO users_equipment(id,u_id,eq_id) VALUES (41,41,567);
create table if not exists messages
(
    id    INTEGER primary key,
    name  VARCHAR not null,
    email VARCHAR not null,
    topic VARCHAR not null,
    text  VARCHAR not null
);
INSERT INTO messages(id,name,email,topic,text) VALUES (101,'Vladimir','vivanov@mail.ru','How to buy?','I cannot buy any items. Please, help me, what should I do?');
INSERT INTO messages(id,name,email,topic,text) VALUES (111,'Vladimir','vivanov@mail.ru','I bought!','Thanks for your responsiveness! like =)');
INSERT INTO messages(id,name,email,topic,text) VALUES (121,'Ilya','ilya-fdmdh@mail.ru','Test review','This is a test review from developers. You all can see it.');
INSERT INTO messages(id,name,email,topic,text) VALUES (131,'Alex','azb@tpu.ru','Snowboard','I have rented the Snowboard Beepo, and it appeared half-broken. Be careful...');
INSERT INTO messages(id,name,email,topic,text) VALUES (141,'anon','anon','Bad','The ski I had leased is gone. What is this???');