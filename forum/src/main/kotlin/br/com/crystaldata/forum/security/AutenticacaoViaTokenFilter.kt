package br.com.crystaldata.forum.security

import br.com.crystaldata.forum.repository.UsuarioRepository
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AutenticacaoViaTokenFilter(
    private val tokenService: TokenService,
    private val usuarioRepository: UsuarioRepository
    ): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = recuperarToken(request)
        println("TOKEN: $token")
        val valido = tokenService.isTokenValido(token)
        println("TOKEN VALIDO: $valido")

        filterChain.doFilter(request, response)
    }

    private fun recuperarToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")//Recuperar o token no cabeçalho
        return if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null
        } else token.substring(7, token.length)
    }
}