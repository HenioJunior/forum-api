package br.com.crystaldata.forum.repository

import br.com.crystaldata.forum.model.Curso
import org.springframework.data.jpa.repository.JpaRepository

interface CursoRepository: JpaRepository<Curso, Long> {
}