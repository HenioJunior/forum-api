package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.model.Usuario
import org.springframework.security.core.userdetails.UserDetails

class DetalhesUsuario(
    private val usuario: Usuario
): UserDetails {
    override fun getAuthorities() = usuario.roles

    override fun getPassword() = usuario.password

    override fun getUsername() = usuario.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled(): Boolean = true
}