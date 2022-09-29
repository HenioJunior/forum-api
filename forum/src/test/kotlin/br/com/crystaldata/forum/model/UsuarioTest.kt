package br.com.crystaldata.forum.model

object UsuarioTest {
    fun build() = Usuario(id = 1, nome = "Joao", email = "jvc.martins", senha = "123")
    fun buildToToken() = Usuario(nome = "ALuno da Silva", email = "aluno@email.com", senha = "123456", roles = mutableListOf(Role(nome = "LEITURA_ESCRITA")))
}