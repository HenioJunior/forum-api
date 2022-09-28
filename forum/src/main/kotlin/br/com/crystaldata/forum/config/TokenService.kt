package br.com.crystaldata.forum.config

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class TokenService {

    fun gerarToken(authentication: Authentication): String {
        TODO()
    }
}