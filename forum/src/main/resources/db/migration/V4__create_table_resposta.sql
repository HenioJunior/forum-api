create table resposta(
id bigint not null auto_increment,
mensagem varchar(300) not null,
data_resposta datetime not null,
usuario_id bigint not null,
topico_id bigint not null,
solucao int not null,
primary key(id),
foreign key(usuario_id) references usuario(id),
foreign key(topico_id) references topico(id)
);