package br.com.crystaldata.forum.controller

import br.com.crystaldata.forum.dto.RespostaDto
import br.com.crystaldata.forum.entity.Resposta
import br.com.crystaldata.forum.service.RespostaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.transaction.Transactional
import javax.validation.Valid


@RestController
@RequestMapping("/respostas")
class RespostaController(
    private val respostaService: RespostaService) {

    @PostMapping
    @Transactional
    fun salvar(
        @RequestBody @Valid respostaDto: RespostaDto
    ): ResponseEntity<Resposta> {
        respostaService.salvar(respostaDto)
        return ResponseEntity.ok().build()
    }
}