package br.com.crystaldata.forum.controller

import br.com.crystaldata.forum.security.TokenService
import br.com.crystaldata.forum.dto.LoginForm
import br.com.crystaldata.forum.dto.TokenDto
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AutenticacaoController(
    private val authManager: AuthenticationManager,
    private val tokenService: TokenService
) {

    @PostMapping
    fun autenticar(@RequestBody @Valid form: LoginForm): ResponseEntity<TokenDto> {
        val dadosLogin = form.converter()

        try {
            val authentication = authManager.authenticate(dadosLogin)
            val token = tokenService.gerarToken(authentication)
            return ResponseEntity.ok(TokenDto(token, "Bearer"))
        } catch (e: AuthenticationException) {
            return ResponseEntity.badRequest().build()
        }
    }
}