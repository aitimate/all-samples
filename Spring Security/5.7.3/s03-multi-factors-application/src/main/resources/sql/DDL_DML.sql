CREATE DATABASE if not exists all_samples;

DROP TABLE if exists all_samples.t_ss573s03_user_credential;
create table all_samples.t_ss573s03_user_credential
(
    id         int auto_increment primary key,
    uid        int         not null,
    acc_type   varchar(16) not null comment '账号类型: 自建账号、手机号、邮箱、Token、(第三方)微信...',
    identifier varchar(32) not null,
    credential varchar(64) not null
) comment '用户认证凭证表';

DROP TABLE if exists all_samples.t_ss573s03_user_authority;
create table all_samples.t_ss573s03_user_authority
(
    id        int auto_increment primary key,
    uid       int         not null,
    authority varchar(16) not null
) comment '用户身份权限表';

insert into all_samples.t_ss573s03_user_credential (id, uid, acc_type, identifier, credential)
values (1, 1, '自建账号', 'user', '{noop}123456'),
       (2, 1, '手机号', '13141592653', '{noop}123456'),
       (3, 1, '邮箱', '549384715@qq.com', '{noop}123456');

insert into all_samples.t_ss573s03_user_authority (id, uid, authority)
values (1, 1, 'READ'),
       (2, 1, 'WRITE');

# DROP TABLE if exists all_samples.t_ss573s03_user_profile;
# create table all_samples.t_ss573s03_user_profile
# (
#     id       int auto_increment primary key,
#     nickname varchar(32)
# ) comment '用户基础信息表';