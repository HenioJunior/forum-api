create table role(
id bigint not null auto_increment,
nome varchar(50) not null,
primary key(id)
);

insert into role(nome) values('LEITURA_ESCRITA');
insert into role(nome) values('LEITURA');