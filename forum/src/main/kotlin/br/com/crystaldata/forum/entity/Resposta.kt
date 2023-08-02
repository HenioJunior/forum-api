package br.com.crystaldata.forum.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Resposta(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val mensagem: String,
    val dataResposta: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    val usuario: Usuario,
    @ManyToOne
    val topico: Topico,
    val solucao: Int
)