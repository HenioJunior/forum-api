package br.com.crystaldata.forum.mapper

import br.com.crystaldata.forum.dto.RespostaDto
import br.com.crystaldata.forum.entity.Resposta
import br.com.crystaldata.forum.service.RespostaService
import br.com.crystaldata.forum.service.TopicoService
import br.com.crystaldata.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class RespostaDtoMapper(
    private val usuarioService: UsuarioService,
    private val topicoService: TopicoService
) : Mapper<RespostaDto, Resposta> {

    override fun map(t: RespostaDto): Resposta {
        return Resposta(
            mensagem = t.mensagem,
            usuario = usuarioService.buscaPorId(t.usuarioId),
            topico = topicoService.findById(t.topicoId),
            solucao = t.solucao
            )
    }
}
