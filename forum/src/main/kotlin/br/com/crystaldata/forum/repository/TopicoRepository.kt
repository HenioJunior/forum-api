package br.com.crystaldata.forum.repository

import br.com.crystaldata.forum.model.Topico
import org.springframework.data.jpa.repository.JpaRepository

interface TopicoRepository: JpaRepository<Topico, Long> {

    fun findByCursoNome(nomeCurso: String): List<Topico>
}