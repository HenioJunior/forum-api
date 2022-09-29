package br.com.crystaldata.forum.service

import br.com.crystaldata.forum.mapper.TopicoFormMapper
import br.com.crystaldata.forum.mapper.TopicoViewMapper
import br.com.crystaldata.forum.repository.TopicoRepository
import io.mockk.mockk

class TopicoServiceTest {

    val topicoRepository: TopicoRepository = mockk()
    val topicoViewMapper: TopicoViewMapper = mockk()
    val topicoFormMapper: TopicoFormMapper = mockk()

    val topicoService =  TopicoService(
            topicoRepository, topicoViewMapper, topicoFormMapper
    )

}