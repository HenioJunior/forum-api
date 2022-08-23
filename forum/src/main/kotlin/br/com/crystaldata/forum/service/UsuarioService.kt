package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.model.Curso
import br.com.crystaldata.forum.model.Usuario
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService(var usuarios: List<Usuario>) {

    init {
        val usuario = Usuario(
            id = 1,
            nome = "Ana da Silva",
            email = "ana@email.com.br"
        )
        usuarios = Arrays.asList(usuario)
    }

    fun buscaPorId(id: Long): Usuario {
        return usuarios.stream().filter {u -> u.id == id}
            .findFirst().get()
    }
}
