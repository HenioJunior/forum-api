package br.com.crystaldata.forum.dto

import br.com.crystaldata.forum.entity.Role

data class UsuarioView(
    val nome: String,
    val email: String,
    val roles: List<Role> = mutableListOf()
)