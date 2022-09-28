package br.com.crystaldata.forum.config

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

@EnableWebSecurity
@Configuration
class SecurityConfiguration {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeRequests { authz -> authz
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers("/topicos")
                .hasAuthority("LEITURA_ESCRITA")
                .anyRequest()
                .authenticated()
            }
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

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