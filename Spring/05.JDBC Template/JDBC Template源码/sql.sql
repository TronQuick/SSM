drop database if exists selection_course;

create database selection_course;
use selection_course;

create table course
(
   id                   int not null auto_increment,
   name                 char(20),
   score                int,
   primary key (id)
);

create table selection
(
   student              int not null,
   course               int not null,
   selection_time       datetime,
   score                int,
   primary key (student, course)
);

create table student
(
   id                   int not null auto_increment,
   name                 varchar(20),
   sex                  char(2),
   born                 date,
   primary key (id)
);

alter table selection add constraint FK_Reference_1 foreign key (course)
      references course (id) on delete restrict on update restrict;

alter table selection add constraint FK_Reference_2 foreign key (student)
      references student (id) on delete restrict on update restrict;

insert into course(id,name,score) values(1001,'英语',5);
insert into course(id,name,score) values(1002,'操作系统',5);
insert into course(id,name,score) values(1003,'数据结构',3);

commit;