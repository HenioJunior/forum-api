## Notas

### Criação da classe SecurityConfiguration

1. Utilizar as anotações de classe: `@EnableWebSecurity` e `@Configuration`

2. Criação do método de autorização(Bloquear ou Liberar recursos):
```kotlin
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeRequests { authz -> authz
                    .anyRequest().authenticated()
            }
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable().httpBasic()
        return http.build()
    }
```

### Implementar a autenticação

1. No projeto, incluir o atributo `password` na classe Usuario, alterar a tabela no banco e inserir o password

2. Implementar a interface `UserDetailsService` na classe UsuarioService

   1. Implementar o método `loadUserByUsername` para buscar as informações do usuário no banco de dados;
   1. Criar a classe DetalhesUsuario, injetar o usuario, implementar a interface `UserDetails` e seus métodos;
   1. Configurar os métodos da interface `UserDetails` na classe DetalhesUsuario; 
   

