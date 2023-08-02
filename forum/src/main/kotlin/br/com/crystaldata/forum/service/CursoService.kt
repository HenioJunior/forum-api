package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.entity.Curso
import br.com.crystaldata.forum.exception.NotFoundException
import br.com.crystaldata.forum.repository.CursoRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CursoService(val repository: CursoRepository) {

    fun buscaPorId(id: Long): Curso {
        return repository.findById(id).orElseThrow { NotFoundException("Curso não encontrado") }
    }
}
