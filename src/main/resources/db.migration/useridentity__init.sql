CREATE TABLE user_identity (
    id serial primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    user_name varchar(255) not null,
    password varchar(255) not null,
    email varchar(255) not null,
    authorities varchar(255) not null,
    active boolean not null
);