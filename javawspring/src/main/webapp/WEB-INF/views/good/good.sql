create table good (
	idx     int not null auto_increment primary key,
	part    varchar(20) not null,
	partIdx int not null,
	mid     varchar(20) not null,
	goodSw char(1) default 'Y'
);

desc good;
drop table good;