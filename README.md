## Notas

Criação da classe SecurityConfiguration
----------------------------------------

   1. Utilizar as anotações de classe: `@EnableWebSecurity` e `@Configuration`
   
   2. Criação do método de autorização(Bloquear ou Liberar recursos):
    ```java
     @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.
        authorizeRequests().
        anyRequest().
        authenticated()
            .and().
            sessionManagement().
            sessionCreationPolicy(SessionCreationPolicy.STATELESS)//Não vou guardar estado
            .and().
            formLogin().disable().httpBasic()
        return http.build()
    }
    ```
    























 







