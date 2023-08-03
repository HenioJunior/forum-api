package br.com.crystaldata.forum.controller

import br.com.crystaldata.forum.dto.TopicoPorCategoria
import br.com.crystaldata.forum.service.TopicoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/relatorios")
class RelatorioController(
    private val topicoService: TopicoService
) {
    @GetMapping
    fun relatorio(): List<TopicoPorCategoria> {
        return topicoService.relatorio()
    }
}