package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.exception.NotFoundException
import br.com.crystaldata.forum.model.Usuario
import br.com.crystaldata.forum.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val repository: UsuarioRepository
    ): UserDetailsService {

    fun buscaPorId(id: Long): Usuario {
        return repository.findById(id)
            .orElseThrow { NotFoundException("Usuário Não Encontrado") }
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        TODO("Not yet implemented")
    }
}
