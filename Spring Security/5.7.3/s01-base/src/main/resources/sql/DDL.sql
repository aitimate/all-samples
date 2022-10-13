CREATE DATABASE if not exists samples;

DROP TABLE if exists samples.t_user;
DROP TABLE if exists samples.t_user_authority;
DROP TABLE if exists samples.t_user_health;

create table samples.t_user
(
    id       int auto_increment primary key,
    username varchar(32) null,
    password varchar(64) null,
    constraint t_user_username_uindex unique (username)
);

create table samples.t_user_authority
(
    id    int auto_increment primary key,
    uid   int         null,
    value varchar(64) null
);
create table samples.t_user_health
(
    id       int auto_increment primary key,
    username varchar(32) null,
    prop     varchar(32) null,
    value    double      null
);