drop table STRUTS2_CONTACT;
drop table STRUTS2_CITY;
drop table STRUTS2_USERS;
drop sequence STRUTS2_CONTACT_SEQ;

create table STRUTS2_USERS
(
	username varchar2(20) primary key,
	password varchar2(20) not null,
	type number(1) not null,
	realname varchar2(20) not null
);

create table STRUTS2_CITY(
       id number(6) primary key,
       name varchar2(20) not null
);

create table STRUTS2_CONTACT
(
  ID    NUMBER(10) primary key,
  NAME  VARCHAR2(20),
  QQ    VARCHAR2(20),
  PHONE VARCHAR2(20),
  EMAIL VARCHAR2(20),
  cityid  number(6) references STRUTS2_city(id),
  photo varchar2(50),
  username varchar2(20) references STRUTS2_users(username) not null
);

create sequence STRUTS2_CONTACT_SEQ;

insert into STRUTS2_users values ('zhangsan', '123', 1, '张三');

insert into STRUTS2_city values (1,'北京');
insert into STRUTS2_city values (2,'天津');
insert into STRUTS2_city values (3,'上海');
insert into STRUTS2_city values (4,'南京');
insert into STRUTS2_city values (5,'成都');

insert into STRUTS2_contact values (STRUTS2_contact_seq.nextval, '张三他爹', '123456789', '18612223344', 'oldzhang@163.com', 1, null, 'zhangsan');

insert into STRUTS2_contact values (STRUTS2_contact_seq.nextval, '张三他妈', '123456789', '18612225555', 'oldzhang@163.com', 2, null, 'zhangsan');
insert into STRUTS2_contact values (STRUTS2_contact_seq.nextval, '爷爷', '123456789', '18612225555', 'oldzhang@163.com', 1, null, 'zhangsan');
insert into STRUTS2_contact values (STRUTS2_contact_seq.nextval, '奶奶', '123456789', '18612225555', 'oldzhang@163.com', 1, null, 'zhangsan');
insert into STRUTS2_contact values (STRUTS2_contact_seq.nextval, '外公', '123456789', '18612225555', 'oldzhang@163.com', 1, null, 'zhangsan');
insert into STRUTS2_contact values (STRUTS2_contact_seq.nextval, '外婆', '123456789', '18612225555', 'oldzhang@163.com', 1, null, 'zhangsan');
insert into STRUTS2_contact values (STRUTS2_contact_seq.nextval, '哥哥', '123456789', '18612225555', 'oldzhang@163.com', 1, null, 'zhangsan');
insert into STRUTS2_contact values (STRUTS2_contact_seq.nextval, '姐姐', '123456789', '18612225555', 'oldzhang@163.com', 1, null, 'zhangsan');
insert into STRUTS2_contact values (STRUTS2_contact_seq.nextval, '弟弟', '123456789', '18612225555', 'oldzhang@163.com', 1, null, 'zhangsan');
insert into STRUTS2_contact values (STRUTS2_contact_seq.nextval, '妹妹', '123456789', '18612225555', 'oldzhang@163.com', 1, null, 'zhangsan');
insert into STRUTS2_contact values (STRUTS2_contact_seq.nextval, '大姨', '123456789', '18612225555', 'oldzhang@163.com', 1, null, 'zhangsan');
insert into STRUTS2_contact values (STRUTS2_contact_seq.nextval, '小姨', '123456789', '18612225555', 'oldzhang@163.com', 1, null, 'zhangsan');
commit;