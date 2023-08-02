package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.dto.RespostaDto
import br.com.crystaldata.forum.entity.Resposta
import br.com.crystaldata.forum.exception.NotFoundException
import br.com.crystaldata.forum.mapper.RespostaDtoMapper
import br.com.crystaldata.forum.repository.RespostaRepository
import br.com.crystaldata.forum.repository.TopicoRepository
import br.com.crystaldata.forum.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class RespostaService(
    private val respostaRepository: RespostaRepository,
    private val respostaDtoMapper: RespostaDtoMapper

) {

    fun salvar(respostaDto: RespostaDto) {
        respostaRepository.save(respostaDtoMapper.map(respostaDto))
    }
}