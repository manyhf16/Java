drop sequence users_seq;
drop table users;
create sequence users_seq;
create table users (
 id number(10) primary key,
 username varchar2(20),
 password varchar2(20),
 updatetime date
);