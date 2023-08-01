package br.com.crystaldata.forum.mapper

import br.com.crystaldata.forum.dto.NovoTopicoForm
import br.com.crystaldata.forum.entity.Topico
import br.com.crystaldata.forum.service.CursoService
import br.com.crystaldata.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class TopicoFormMapper(
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService
) : Mapper<NovoTopicoForm, Topico> {

    override fun map(t: NovoTopicoForm): Topico {
        return Topico(
            titulo = t.titulo,
            mensagem = t.mensagem,
            curso = cursoService.buscaPorId(t.idCurso),
            autor = usuarioService.buscaPorId(t.idAutor)
        )
    }
}
