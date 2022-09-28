package br.com.crystaldata.forum.controller

import br.com.crystaldata.forum.dto.LoginForm
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AutenticacaoController {

    @PostMapping
    fun autenticar(@RequestBody @Valid form: LoginForm): ResponseEntity<String> {


        return ResponseEntity.ok().body("OK")
    }

}