package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.exception.NotFoundException
import br.com.crystaldata.forum.model.Usuario
import br.com.crystaldata.forum.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class UsuarioService(
    private val repository: UsuarioRepository,
    private val notFoundMessage: String = "Usuário Não Encontrado"
    ): UserDetailsService {

    fun buscaPorId(id: Long): Usuario {
        return repository.findById(id)
            .orElseThrow { NotFoundException(notFoundMessage) }
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = repository.findByEmail(username) ?: throw NotFoundException(notFoundMessage)
        return DetalhesUsuario(usuario)
    }
}
