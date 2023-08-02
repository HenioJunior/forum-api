package br.com.crystaldata.forum.repository

import br.com.crystaldata.forum.entity.Resposta
import org.springframework.data.jpa.repository.JpaRepository

interface RespostaRepository: JpaRepository<Resposta, Long>