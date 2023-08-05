package br.com.crystaldata.forum.controller

import br.com.crystaldata.forum.dto.LoginForm
import br.com.crystaldata.forum.security.TokenService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TopicoControllerTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Value("\${jwt.expiration}")
    lateinit var expiration: String

    @Value("\${jwt.secret}")
    lateinit var secret: String

    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var authManager: AuthenticationManager


    private var token: String? = null

    companion object {
        private const val RECURSO = "/topicos/"
    }

    @BeforeEach
    fun setup() {
        token = gerarToken()

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder>(
                SecurityMockMvcConfigurers.springSecurity()
            ).build()
    }

    @Test
    fun `deve retornar codigo 400 qdo chamar topicos sem token`(){
        mockMvc.get(RECURSO).andExpect { status { is4xxClientError() } }
    }


    @Test
    fun `deve retornar codigo 200 quando chamar topicos com token`() {
        mockMvc.get(RECURSO) {
            headers { token?.let { this.setBearerAuth(it) } }
        }.andExpect { status { is2xxSuccessful() } }
    }

    fun gerarToken(): String {
        val form: LoginForm = LoginForm("ana@email.com", "123456")
        val dadosLogin = form.converter()
        val authenticate = authManager.authenticate(dadosLogin)
        return tokenService.gerarToken(authenticate)
    }
}