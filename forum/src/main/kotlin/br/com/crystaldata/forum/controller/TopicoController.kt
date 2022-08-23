package br.com.crystaldata.forum.controller

import br.com.crystaldata.forum.dto.AtualizacaoTopicoForm
import br.com.crystaldata.forum.dto.NovoTopicoForm
import br.com.crystaldata.forum.dto.TopicoView
import br.com.crystaldata.forum.service.TopicoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/topicos")
class TopicoController(private val service: TopicoService) {

    @GetMapping
    fun listar(): List<TopicoView> {
        return service.listar()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView {
        return service.buscarPorId(id)
    }

    @PostMapping
    fun cadastrar(@RequestBody @Valid form: NovoTopicoForm) {
        service.cadastrar(form)
    }

    @PutMapping
    fun atualizar(@RequestBody @Valid form: AtualizacaoTopicoForm) {
        service.atualizar(form)
    }
}