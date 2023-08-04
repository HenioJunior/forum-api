package br.com.crystaldata.forum.integration

import br.com.crystaldata.forum.dto.TopicoPorCategoriaDto
import br.com.crystaldata.forum.entity.TopicoTest
import br.com.crystaldata.forum.repository.TopicoRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class TopicoRepositoryTest {

    private val topico = TopicoTest.build()

    @Autowired
    private lateinit var topicoRepository: TopicoRepository

    companion object {

        @Container
        private val mySQLContainer = MySQLContainer<Nothing>("mysql:8.0.30").apply {
            withDatabaseName("db_test")
            withUsername("test")
            withPassword("P@ssw0rd")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            //val port = mySQLContainer.getMappedPort(3306)
            registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", mySQLContainer::getUsername)
            registry.add("spring.datasource.password", mySQLContainer::getPassword)
        }
    }

    @Test
    fun testMySQLContainerIsRunning() {
        assertThat(mySQLContainer.isRunning).isTrue()
    }

    @Test
    fun `deve gerar relatorio`() {
        topicoRepository.save(topico)
        val relatorio = topicoRepository.relatorio()

        assertThat(relatorio).isNotNull
        assertThat(relatorio.first()).isExactlyInstanceOf(TopicoPorCategoriaDto::class.java)
    }

    @Test
    fun `deve listar topico pelo nome do curso`() {
        topicoRepository.save(topico)
        val topico = topicoRepository.findByCursoNome(topico.curso.nome, PageRequest.of(0, 3))

        assertThat(topico).isNotNull
    }
}