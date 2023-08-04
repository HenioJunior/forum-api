package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.dto.AtualizacaoTopicoForm
import br.com.crystaldata.forum.dto.NovoTopicoForm
import br.com.crystaldata.forum.dto.TopicoPorCategoriaDto
import br.com.crystaldata.forum.dto.TopicoView
import br.com.crystaldata.forum.entity.Topico
import br.com.crystaldata.forum.exception.NotFoundException
import br.com.crystaldata.forum.mapper.TopicoFormMapper
import br.com.crystaldata.forum.mapper.TopicoViewMapper
import br.com.crystaldata.forum.repository.TopicoRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TopicoService(
    private val repository: TopicoRepository,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "Topico não encontrado"
) {

    @Cacheable(cacheNames = ["Topicos"], key = "#root.method.name")
    fun listar(
        nomeCurso: String?,
        paginacao: Pageable
    ): Page<TopicoView> {
        val topicos = nomeCurso?.let {
            repository.findByCursoNome(nomeCurso, paginacao)
        } ?: repository.findAll(paginacao)
        return topicos.map { t -> topicoViewMapper.map(t) }
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico = repository.findById(id)
            .orElseThrow { NotFoundException(notFoundMessage) }
        return topicoViewMapper.map(topico)
    }

    fun findById(id: Long): Topico {
        return repository.findById(id)
            .orElseThrow { NotFoundException(notFoundMessage) }
    }


    @CacheEvict(cacheNames = ["Topicos"], allEntries = true)
    fun cadastrar(form: NovoTopicoForm): TopicoView {
        var topico = topicoFormMapper.map(form)
        repository.save(topico)
        return topicoViewMapper.map(topico)
    }

    @CacheEvict(cacheNames = ["Topicos"], allEntries = true)
    fun atualizar(form: AtualizacaoTopicoForm): TopicoView {
        val topico = repository.findById(form.id)
            .orElseThrow { NotFoundException(notFoundMessage) }
        topico.titulo = form.titulo
        topico.mensagem = form.mensagem
        topico.dataAlteracao = LocalDate.now()
        return topicoViewMapper.map(topico)
    }

    @CacheEvict(cacheNames = ["Topicos"], allEntries = true)
    fun deletar(id: Long) {
       repository.deleteById(id)
    }

    fun relatorio(): List<TopicoPorCategoriaDto> {
        return repository.relatorio()
    }
}