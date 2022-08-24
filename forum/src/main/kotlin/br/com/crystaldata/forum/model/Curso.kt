package br.com.crystaldata.forum.model

import javax.persistence.*

@Entity
@Table(name="tb_curso")
data class Curso(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nome: String,
    val categoria: String
)