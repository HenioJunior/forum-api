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
    private val respostaDtoMapper: RespostaDtoMapper,
    private val emailService: EmailService

) {

    fun salvar(respostaDto: RespostaDto) {
        val resposta = respostaDtoMapper.map(respostaDto)
        respostaRepository.save(resposta)

        val usuario = resposta.usuario.email
        emailService.notificar(usuario);
    }
}