package br.com.crystaldata.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val nome: String,
    val email: String,
    val password: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_role",
        joinColumns = [JoinColumn(name = "usuario_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    @JsonIgnore
    val roles: List<Role> = mutableListOf()
)