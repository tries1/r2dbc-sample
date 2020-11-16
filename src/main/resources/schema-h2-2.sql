create table user2 (
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    age INT NOT NULL
);

insert into user2 (name, age) values ('test1', 10);
insert into user2 (name, age) values ('test2', 20);
insert into user2 (name, age) values ('test3', 30);