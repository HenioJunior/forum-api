package br.com.crystaldata.forum.dto

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

data class LoginForm(
    val email: String,
    val senha: String
) {
    fun converter(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, senha)
    }
}