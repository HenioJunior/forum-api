package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.dto.NovoTopicoDto
import br.com.crystaldata.forum.model.Topico
import org.springframework.stereotype.Service

@Service
class TopicoService(
    private var topicos: List<Topico> = ArrayList(),
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService
) {

    fun listar(): List<Topico> {
        return topicos
    }

    fun buscarPorId(id: Long): Topico {
        return topicos.stream().filter({ t ->
            t.id == id
        }).findFirst().get()
    }

    fun cadastrar(dto: NovoTopicoDto) {
        topicos = topicos.plus(
            Topico(
                id = topicos.size.toLong() + 1,
                titulo = dto.titulo,
                mensagem = dto.mensagem,
                curso = cursoService.buscaPorId(dto.idCurso),
                autor = usuarioService.buscaPorId(dto.idAutor)
            )
        )
    }


}