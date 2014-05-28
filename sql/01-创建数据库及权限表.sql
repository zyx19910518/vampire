drop database if exists vampire;
create database vampire;
use vampire;
create table `user` (
  `id` int(11) not null auto_increment,
  `username` varchar(20) not null,
  `nickname` varchar(20),
  `password` varchar(50) not null,
  `enabled` tinyint(1) not null,
  `register_time` timestamp not null default current_timestamp on update current_timestamp,
  primary key (`id`)
) engine=innodb default charset=utf8;

create table `role` (
  `id` int(11) not null auto_increment,
  `name` varchar(50) not null,
  `code` varchar(20) not null,
  primary key (`id`)
) engine=innodb default charset=utf8;

create table `user_role` (
  `id` int(11) not null auto_increment,
  `user_id` int(11) not null,
  `role_id` int(11) not null,
  primary key (`id`)
) engine=innodb default charset=utf8;

create table `resources` (
  `id` int(11) not null auto_increment,
  `name` varchar(50) not null,
  `url` varchar(255) not null comment '资源url',
  `comment` varchar(255) default null,
  primary key (`id`)
) engine=innodb default charset=utf8;

create table `roles_resources` (
  `id` int(11) not null auto_increment,
  `role_id` int(11) not null,
  `resource_id` int(11) not null,
  primary key (`id`)
) engine=innodb default charset=utf8;
