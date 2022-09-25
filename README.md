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

2. Criar a classe DetalhesUsuario, injetar o usuario, implementar a interface `UserDetails` e seus métodos; 

    1. Configurar os métodos da interface `UserDetails` na classe DetalhesUsuario;

3. Implementar a interface `UserDetailsService` na classe UsuarioService

   1. Implementar a assinatura do método `findByEmail` em UsuarioRepository
   1. Implementar o método `loadUserByUsername` para buscar as informações do usuário no banco de dados;
   ```kotlin
   override fun loadUserByUsername(username: String?): UserDetails {
        
        val usuario = repository.findByEmail(username) 
        ?: throw NotFoundException(notFoundMessage)
        
        return DetalhesUsuario(usuario)
    }
   ```  
4. Na classe SecurityConfiguration, implementar o Password Encoder. Neste ponto, consigo fazer uma autenticação básica no endpoint
   ```kotlin
   @Bean
    fun encoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
   ```

5. Criar as Roles de usuário
    1. Criar a classe Role;
    1. Em migration, criar a tabela `usuario_role`;
    1. Criar o atributo `roles` na classe Usuario;
    ```kotlin
    @ManyToMany(fetch = FetchType.EAGER)//EAGER para carregar o usuario e todas as suas roles
    @JoinTable(name = "usuario_role",
        joinColumns = [JoinColumn(name = "usuario_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    @JsonIgnore
    val roles: List<Role> = mutableListOf()
    ``` 
    
6. Implementar a interface `GrantedAuthority` na classe `Role`
   1. Implementar o método `getAuthority()` que vai receber o `nome`da Role ;
   2. Na classe `DetalhesUsuario` o método `getAuthorities()` vai receber `usuario.roles`;  
  
7. Implementado o endpoint para listar os Usuarios e suas Roles;

8.  
   

