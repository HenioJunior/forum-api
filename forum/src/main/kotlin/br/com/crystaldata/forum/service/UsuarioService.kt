package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.model.Usuario
import br.com.crystaldata.forum.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class UsuarioService(private val repository: UsuarioRepository) {

    fun buscaPorId(id: Long): Usuario {
        return repository.getOne(id)
    }
}
