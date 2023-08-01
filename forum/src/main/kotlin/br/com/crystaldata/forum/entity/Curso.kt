package br.com.crystaldata.forum.entity

import javax.persistence.*

@Entity
data class Curso(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nome: String,
    val categoria: String
)