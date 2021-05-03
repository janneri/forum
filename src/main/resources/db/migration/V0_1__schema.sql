drop table if exists messageboard;
drop table if exists message;
drop table if exists forum_user;

create table messageboard (
    messageboard_id int auto_increment primary key,
    name varchar(250) not null
);

create table forum_user (
    user_id int primary key,
    name varchar(250) not null
);

create table message (
    message_id int auto_increment primary key,
    messageboard_id int not null,
    user_id int not null,
    text varchar(250) not null,
    create_time timestamp not null,
    constraint messageboard_fk foreign key (messageboard_id) references messageboard(messageboard_id),
    constraint forum_user_fk foreign key (user_id) references forum_user(user_id)
);