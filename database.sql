create schema acms;
use acms;

create table user
(
    id          varchar(40) primary key,
    name        varchar(100) not null,
    password    varchar(100) not null,
    authorities varchar(100) not null,
    gender      varchar(10),
    phone       varchar(20),
    description varchar(500)
);

create table device
(
    id          varchar(40) primary key,
    name        varchar(100) not null,
    description varchar(500)
);

create table access_info
(
    device_id   varchar(40),
    user_id     varchar(40),
    description varchar(500),
    primary key (device_id, user_id),
    constraint foreign key (device_id) references device (id) on update cascade on delete cascade,
    constraint foreign key (user_id) references user (id) on update cascade on delete cascade
);
