package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.model.Curso
import br.com.crystaldata.forum.model.Topico
import br.com.crystaldata.forum.model.Usuario
import org.springframework.stereotype.Service
import java.util.*

@Service
class TopicoService(private var topicos: List<Topico>) {

    init {
        val topico1 = Topico(
            id = 1,
            titulo = "Duvida Kotlin1",
            mensagem = "Variaveis do Kotlin1",
            curso = Curso(
                id = 1,
                nome = "Kotlin",
                categoria = "Programação"
            ),
            autor = Usuario(
                id = 1,
                nome = "Ana da Silva",
                email = "ana@email.com"
            )
        )

        val topico2 = Topico(
            id = 2,
            titulo = "Duvida Kotlin2",
            mensagem = "Variaveis do Kotlin2",
            curso = Curso(
                id = 1,
                nome = "Kotlin",
                categoria = "Programação"
            ),
            autor = Usuario(
                id = 1,
                nome = "Ana da Silva",
                email = "ana@email.com"
            )
        )

        val topico3 = Topico(
            id = 3,
            titulo = "Duvida Kotlin3",
            mensagem = "Variaveis do Kotlin3",
            curso = Curso(
                id = 1,
                nome = "Kotlin",
                categoria = "Programação"
            ),
            autor = Usuario(
                id = 1,
                nome = "Ana da Silva",
                email = "ana@email.com"
            )
        )
        topicos = Arrays.asList(topico1, topico2, topico3)
    }


    fun listar(): List<Topico> {
        return topicos
    }

    fun buscarPorId(id: Long): Topico {
        return topicos.stream().filter({
            t -> t.id == id
        }).findFirst().get()
    }


}