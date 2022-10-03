package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.exception.NotFoundException
import br.com.crystaldata.forum.mapper.TopicoFormMapper
import br.com.crystaldata.forum.mapper.TopicoViewMapper
import br.com.crystaldata.forum.model.TopicoTest
import br.com.crystaldata.forum.model.TopicoViewTest
import br.com.crystaldata.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

class TopicoServiceTest {

    val topicos = PageImpl(listOf(TopicoTest.build()))

    val paginacao: Pageable = mockk()

    val topicoRepository: TopicoRepository = mockk {
        every { findByCursoNome(any(), any()) } returns topicos
        every { findAll(paginacao) } returns topicos
    }
    val topicoViewMapper: TopicoViewMapper = mockk {
        every { map(any()) } returns TopicoViewTest.build()
    }
    val topicoFormMapper: TopicoFormMapper = mockk()

    val topicoService =  TopicoService(
            topicoRepository, topicoViewMapper, topicoFormMapper
    )

    @Test
    fun `deve listar topicos a partir do nome do curso`() {
        val listar = topicoService.listar("Kotlin Basico", paginacao)

        verify (exactly = 1) { topicoRepository.findByCursoNome(any(),any()) }
        verify(exactly = 1) { topicoViewMapper.map(any()) }
        verify(exactly = 0) { topicoRepository.findAll(paginacao) }
    }

    @Test
    fun `deve listar todos os topicos quando o nome do curso for nulo`() {
        val listar = topicoService.listar(null, paginacao)

        verify (exactly = 0) { topicoRepository.findByCursoNome(any(),any()) }
        verify(exactly = 1) { topicoViewMapper.map(any()) }
        verify(exactly = 1) { topicoRepository.findAll(paginacao) }
    }
}