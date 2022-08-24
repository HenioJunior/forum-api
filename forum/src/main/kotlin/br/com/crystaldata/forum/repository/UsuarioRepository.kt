package br.com.crystaldata.forum.repository

import br.com.crystaldata.forum.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Long> {
}