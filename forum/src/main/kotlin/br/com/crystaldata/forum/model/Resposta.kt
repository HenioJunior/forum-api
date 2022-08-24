package br.com.crystaldata.forum.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name="tb_resposta")
data class Resposta(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val mensagem: String,
    val dataResposta: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    val autor: Usuario,
    @ManyToOne
    val topico: Topico,
    val solucao: Boolean
)