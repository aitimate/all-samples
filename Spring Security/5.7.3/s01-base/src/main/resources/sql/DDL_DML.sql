CREATE DATABASE if not exists all_samples;

DROP TABLE if exists all_samples.t_ss573s01_user;
DROP TABLE if exists all_samples.t_ss573s01_user_authority;
DROP TABLE if exists all_samples.t_ss573s01_user_health;

create table all_samples.t_ss573s01_user
(
    id       int auto_increment primary key,
    username varchar(32) null,
    password varchar(64) null,
    constraint t_ss573s01_user_username_uindex unique (username)
);

create table all_samples.t_ss573s01_user_authority
(
    id    int auto_increment primary key,
    uid   int         null,
    authority varchar(64) null
);

create table all_samples.t_ss573s01_user_health
(
    id       int auto_increment primary key,
    username varchar(32) null,
    prop     varchar(32) null,
    value    double      null
);

# -------------------------------------------------------------------------

insert into all_samples.t_ss573s01_user (id, username, password)
values (1, 'user', '{noop}123456');

insert into all_samples.t_ss573s01_user_authority (id, uid, authority)
values (1, 1, 'READ'),
       (2, 1, 'WRITE');

insert into all_samples.t_ss573s01_user_health (id, username, prop, value)
values (1, 'user', 'weight', '70'),
       (2, 'user', 'height', '175'),
       (3, 'user', 'bloodPressure', '115'),
       (4, 'user', 'pause', '85');

# --------------------------------------------------------------------------