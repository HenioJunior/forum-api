package br.com.crystaldata.forum.controller

import br.com.crystaldata.forum.dto.UsuarioView
import br.com.crystaldata.forum.model.Usuario
import br.com.crystaldata.forum.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/usuarios")
class UsuarioController(private val service: UsuarioService) {

    @GetMapping
    fun listar(): ResponseEntity<List<UsuarioView>> {
        val usuarios = service.listar()
        return ResponseEntity.ok().body(usuarios)
    }
}