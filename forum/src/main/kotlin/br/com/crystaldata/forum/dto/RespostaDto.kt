package br.com.crystaldata.forum.dto

import java.time.LocalDateTime

data class RespostaDto(
    val mensagem: String,
    val dataResposta: LocalDateTime = LocalDateTime.now(),
    val usuarioId: Long,
    val topicoId: Long,
    val solucao: Int
)