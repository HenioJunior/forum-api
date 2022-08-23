package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.dto.AtualizacaoTopicoForm
import br.com.crystaldata.forum.dto.NovoTopicoForm
import br.com.crystaldata.forum.dto.TopicoView
import br.com.crystaldata.forum.mapper.TopicoFormMapper
import br.com.crystaldata.forum.mapper.TopicoViewMapper
import br.com.crystaldata.forum.model.Topico
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class TopicoService(
    private var topicos: List<Topico> = ArrayList(),
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper
) {

    fun listar(): List<TopicoView> {
        return topicos.stream().map { t -> topicoViewMapper.map(t) }
            .collect(Collectors.toList())
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico = topicos.stream().filter { t -> t.id == id }
            .findFirst().get()
        return topicoViewMapper.map(topico)
    }

    fun cadastrar(form: NovoTopicoForm) {
        var topico = topicoFormMapper.map(form)
        topico.id = topicos.size.toLong() + 1
        topicos = topicos.plus(topico)
    }

    fun atualizar(form: AtualizacaoTopicoForm) {
        val topico = topicos.stream().filter { t -> t.id == form.id }
            .findFirst().get()
        topicos = topicos.minus(topico).plus(
            Topico(
                id = form.id,
                titulo = form.titulo,
                mensagem = form.mensagem,
                autor = topico.autor,
                curso = topico.curso,
                respostas = topico.respostas,
                status = topico.status,
                dataCriacao = topico.dataCriacao
            )
        )
    }
}