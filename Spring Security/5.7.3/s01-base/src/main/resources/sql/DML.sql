insert into t_user (id, username, password)
values (1, 'foo', '{noop}123456');

insert into t_user_authority (id, uid, value)
values (1, 1, 'READ'),
       (2, 1, 'WRITE');

insert into t_user_health (id, username, prop, value)
values (1, 'foo', 'weight','70'),
       (2, 'foo', 'height','175'),
       (3, 'foo', 'bloodPressure','115'),
       (4, 'foo', 'pause','85');