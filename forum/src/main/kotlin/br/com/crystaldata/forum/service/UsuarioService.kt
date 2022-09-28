package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.dto.UsuarioView
import br.com.crystaldata.forum.exception.NotFoundException
import br.com.crystaldata.forum.mapper.UsuarioViewMapper
import br.com.crystaldata.forum.model.Usuario
import br.com.crystaldata.forum.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class UsuarioService(
    private val repository: UsuarioRepository,
    private val usuarioViewMapper: UsuarioViewMapper,
    private val notFoundMessage: String = "Usuário Não Encontrado"
    ): UserDetailsService {

    fun listar(): List<UsuarioView> {
        val usuarios = repository.findAll()
        return usuarios.map { usuario -> usuarioViewMapper.map(usuario) }
    }

    fun buscaPorId(id: Long): Usuario {
        return repository.findById(id)
            .orElseThrow { NotFoundException(notFoundMessage) }
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        return repository.findByEmail(username) ?: throw NotFoundException(notFoundMessage)
    }
}
