INSERT INTO usuario(nome, email, senha) VALUES('Admin', 'admin@email.com', '$2a$12$7Tk./hwGuWZmwNs3db59h.pB66MMPSEkC0cntEODT8F791/9ZXFZa');
INSERT INTO role(nome) VALUES('ADMIN');
INSERT INTO `usuario_role`(usuario_id, role_id) VALUES(3, 3);