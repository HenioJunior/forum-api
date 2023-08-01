package br.com.crystaldata.forum.security

import br.com.crystaldata.forum.entity.Usuario
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {

    @Value("\${jwt.expiration}")
    lateinit var expiration: String

    @Value("\${jwt.secret}")
    lateinit var secret: String

    fun gerarToken(authentication: Authentication): String {
        val usuarioLogado: Usuario = authentication.principal as Usuario
        val hoje = Date()
        val dataExpiracao = Date(hoje.time.plus(expiration.toLong()))

        return Jwts.builder()
            .setIssuer("Fórum API")
            .setSubject(usuarioLogado.username)
            .setIssuedAt(hoje)
            .setExpiration(dataExpiracao)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun isTokenValido(token: String?): Boolean {
        return try {
            Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUsuario(token: String?): String {
        val claims = Jwts
            .parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
        return claims.subject
    }
}