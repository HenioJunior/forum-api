package br.com.crystaldata.forum.controller

import br.com.crystaldata.forum.model.Curso
import br.com.crystaldata.forum.model.Topico
import br.com.crystaldata.forum.model.Usuario
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Arrays

@RestController
@RequestMapping("/topicos")
class TopicoController {

    @GetMapping
    fun listar(): List<Topico> {
        val topico = Topico(
            id = 1,
            titulo = "Duvida Kotlin",
            mensagem = "Variaveis do Kotlin",
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

        return Arrays.asList(topico, topico, topico)
    }

}