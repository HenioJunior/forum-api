package br.com.crystaldata.forum.repository

import br.com.crystaldata.forum.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Long> {

    fun findByEmail(username: String?): Usuario?
}