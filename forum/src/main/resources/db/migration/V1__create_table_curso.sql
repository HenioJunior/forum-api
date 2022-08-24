create table curso(
id bigint not null auto_increment,
nome varchar(50) not null,
categoria varchar(50) not null,
primary key(id)
);

insert into curso(nome, categoria) values('Spring Boot', 'Programação');
insert into curso(nome, categoria) values('HTML 5', 'Front-end');
