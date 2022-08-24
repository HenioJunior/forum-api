package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.model.Curso
import br.com.crystaldata.forum.repository.CursoRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CursoService(val repository: CursoRepository) {

    fun buscaPorId(id: Long): Curso {
        return repository.getOne(id)
    }
}
