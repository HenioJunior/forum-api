package br.com.crystaldata.forum.entity

object TopicoTest {
    fun build() = Topico(
        id = 1,
        titulo = "Kotlin Basico",
        mensagem = "Aprendendo kotlin basico",
        curso = CursoTest.build(),
        usuario = UsuarioTest.build()
    )
}