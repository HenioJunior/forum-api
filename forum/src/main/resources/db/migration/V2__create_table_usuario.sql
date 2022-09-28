create table usuario(
id bigint not null auto_increment,
nome varchar(50) not null,
email varchar(50) not null,
senha text not null,
primary key(id)
);

insert into usuario(nome, email, senha) values('Aluno', 'aluno@email.com', '$2a$12$7Tk./hwGuWZmwNs3db59h.pB66MMPSEkC0cntEODT8F791/9ZXFZa');
insert into usuario(nome, email, senha) values('Ana', 'ana@email.com', '$2a$12$7Tk./hwGuWZmwNs3db59h.pB66MMPSEkC0cntEODT8F791/9ZXFZa');

