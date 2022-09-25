package br.com.crystaldata.forum.mapper

import br.com.crystaldata.forum.dto.UsuarioView
import br.com.crystaldata.forum.model.Usuario
import org.springframework.stereotype.Component

@Component
class UsuarioViewMapper: Mapper<Usuario, UsuarioView> {

    override fun map(t: Usuario): UsuarioView {
        return UsuarioView(
            nome = t.nome,
            email = t.email,
            roles = t.roles
        )
    }
}