package br.com.crystaldata.forum.model

import java.time.LocalDateTime

data class Resposta(
    val id: Long?,
    val mensagem: String,
    val dataResposta: LocalDateTime = LocalDateTime.now(),
    val autor: Usuario,
    val topico: Topico,
    val solucao: Boolean
)