CREATE TABLE members (
    id serial primary key not null,
    member_name varchar(100) not null,
    email varchar(500) not null unique
);