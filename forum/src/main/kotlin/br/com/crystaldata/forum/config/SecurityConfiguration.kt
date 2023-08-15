package br.com.crystaldata.forum.config

import br.com.crystaldata.forum.repository.UsuarioRepository
import br.com.crystaldata.forum.security.AutenticacaoViaTokenFilter
import br.com.crystaldata.forum.security.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfiguration(
    private val tokenService: TokenService,
    private val usuarioRepository: UsuarioRepository
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeRequests { authz -> authz
                .antMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                .antMatchers("/topicos").hasAuthority("LEITURA_ESCRITA")
                .antMatchers("/relatorios").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/auth")
                .permitAll()
                .anyRequest()
                .authenticated()
            }
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(
                AutenticacaoViaTokenFilter(
                tokenService = tokenService,
                usuarioRepository = usuarioRepository),
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }

    @Bean
    fun encoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(auth: AuthenticationConfiguration): AuthenticationManager {
        return auth.authenticationManager
    }
}