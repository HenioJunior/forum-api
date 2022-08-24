create table topico(
id bigint not null auto_increment,
titulo varchar(50) not null,
mensagem varchar(300) not null,
data_criacao datetime not null,
status varchar(20) not null,
curso_id bigint not null,
autor_id bigint not null,
primary key(id),
foreign key(curso_id) references curso(id),
foreign key(autor_id) references usuario(id)
);

insert into topico(titulo, mensagem, data_criacao, status, autor_id, curso_id) values('Dúvida', 'Erro ao criar projeto', '2019-05-05 18:00:00', 'NAO_RESPONDIDO', 1, 1);
insert into topico(titulo, mensagem, data_criacao, status, autor_id, curso_id) values('Dúvida 2', 'Projeto não compila', '2019-05-05 19:00:00', 'NAO_RESPONDIDO', 1, 1);
insert into topico(titulo, mensagem, data_criacao, status, autor_id, curso_id) values('Dúvida 3', 'Tag HTML', '2019-05-05 20:00:00', 'NAO_RESPONDIDO', 1, 2);

