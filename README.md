## Notas

### Criação da classe SecurityConfiguration

1. Utilizar as anotações de classe: `@EnableWebSecurity` e `@Configuration`

- A classe extende `SecurityFilterChain`

2. Criação do método de autorização(Liberar recursos):
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


1. Implementar a interface `UserDetails` e seus métodos na classe `Usuario`; 
  
2. Implementar a interface `UserDetailsService` na classe `UsuarioService`;

   1. Implementar a assinatura do método `findByEmail` em UsuarioRepository
   
   1. Implementar o método `loadUserByUsername` para buscar as informações do usuário no banco de dados;
   ```kotlin
   override fun loadUserByUsername(username: String?): UserDetails {
        return repository.findByEmail(username)
        ?: throw ResourceNotFoundException("Usuario não encontrado")
    }
   ```  
3. Na classe SecurityConfiguration, implementar o Password Encoder.
   ```kotlin
   @Bean
    fun encoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
   ```
  
- Neste ponto, consigo fazer uma autenticação básica no endpoint


4. Criar as Roles de usuário
    1. Criar a classe Role;
    2. Em migration, criar a tabela `usuario_role`;
    3. Criar o atributo `roles` na classe Usuario;
    ```kotlin
    @ManyToMany(fetch = FetchType.EAGER)//EAGER para carregar o usuario e todas as suas roles
    @JoinTable(name = "usuario_role",
        joinColumns = [JoinColumn(name = "usuario_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    @JsonIgnore
    val roles: List<Role> = mutableListOf()
    ``` 
    
5. Implementar a interface `GrantedAuthority` na classe `Role`
   1. Implementar o método `getAuthority()` que vai receber o nome da `Role` ;
   2. Na classe `Usuario` o método `getAuthorities()` vai receber `usuario.roles`;  
  
6. Implementado o endpoint para listar os Usuarios e suas Roles;

### Configurando a autenticação Stateless

1. Instalação da biblioteca jjwt;
```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>
```
2. Em `SecurityConfiguration` vou remover o formLogin(), pois utilizaremos autenticação do tipo Stateless
```kotlin
.sessionManagement()
    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//https://owasp.org/www-community/attacks/csrf
    .and()
    .csrf().disable()
``` 

#### Criação da classe dto `LoginRequest`;
   
   - Criação do método `converter`
   ```kotlin
   fun converter(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, senha)//Uma implementação projetada para apresentação simples de um nome de usuário e senha
    }
   ```

#### Criação do `AutenticacaoController`

1. Criação do método `autenticar(@RequestBody @Valid form: LoginForm): ResponseEntity<?>`
   
    - O objetivo deste método é pegar o login e a senha, autenticar no sistema(verificar no banco) e estando tudo ok eu gero o token;
   
2. Liberar o caminho `/auth` em SecurityConfigurations: ` .antMatchers("/auth").permitAll()`
   
#### No Spring, não é possível injetar automaticamente o `AuthenticationManager`
   
1. Em `SecurityConfiguration` criar o método `authenticationManager` e em seguida, injetar no construtor do `Controller`;
```kotlin
@Bean
fun authenticationManager(auth: AuthenticationConfiguration): AuthenticationManager {
    return auth.authenticationManager
}
```

#### Em `AutenticacaoController`

1. Injeto o `AuthenticationManager` no construtor; //Disparo o processo de autenticação
```kotlin
private val authManager: AuthenticationManager
```

2. No método `autenticar`    
```kotlin
 @PostMapping
    fun autenticar(@RequestBody @Valid form: LoginForm): ResponseEntity<TokenDto>{//Criar a classe TokenDto
        val dadosLogin = val dadosLogin = form.converter() 
        
        try {
            val authentication = authManager.authenticate(dadosLogin)
            val token = tokenService.gerarToken(authentication)//Criar a classe TokenService e injetar na classe
            return ResponseEntity.ok(TokenDto(token, "Bearer"))
        } catch (e: AuthenticationException) {
            return ResponseEntity.badRequest().build()
        }
    }
```
   1. O objeto do tipo`UsernamePasswordAuthenticationToken` vai receber como parâmetro o `email` e a `senha`;
   2. `authManager.authenticate(dadosLogin)` é o método que fará a autenticação;
   3. Tratamento de Exceção para o caso de login/senha inválidos;

#### Geração do Token

1. Criação da classe `TokenService`(anotar com `@Service`) e do método `gerarToken`

```kotlin
//Injeção dos atributos configurados em application.properties;
@Value("\${jwt.expiration}")
lateinit var expiration: String

@Value("\${jwt.secret}")
lateinit var secret: String

fun gerarToken(authentication: Authentication): String {
    //Recuperar o usuário que esta logado;
    //Ele devolve um Object e com isso, é necessário o cast para Usuario;
    val usuarioLogado: Usuario = authentication.principal as Usuario
    val hoje = Date()
    val dataExpiracao = Date(hoje.time.plus(expiration.toLong()))
    //Jwts.builder()é um método utilizado para construir o token;
    return  Jwts.builder()
        .setIssuer("Fórum API")
        .setSubject(usuarioLogado.username)
        .setIssuedAt(hoje)
        .setExpiration(dataExpiracao)
        .signWith(SignatureAlgorithm.HS256, secret)
        //a propriedade compact() transforma para uma String;
        .compact()
    }
``` 

#### Recuperando o token do header Authorization

1. Criação da classe `AutenticacaoViaTokenFilter: OncePerRequestFilter()`;

2. Implementar o método `doFilterInternal()`;
```kotlin
     val token = recuperarToken(request)// Criar a função recuperarToken(3)
        println("TOKEN: $token")
        val valido = tokenService.isTokenValido(token) //Validar o token(4)
        if(valido) {
            autenticarCliente(token)//Autentica o cliente(6)
        }
        filterChain.doFilter(request, response)
```
3. Implementar a função `recuperarToken`
```kotlin
private fun recuperarToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")//Recuperar o token no cabeçalho
        return if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null
        } else token.substring(7, token.length)
    }
``` 

4. Implementar a função `isTokenValido`
```kotlin
fun isTokenValido(token: String?): Boolean {
        return try {
            Jwts
            .parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }
```

5. Criar a função `getUsuario` em `TokenService`
```kotlin
fun getUsuario(token: String?): String {
    val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    return claims.subject
    }
```


6. Implementar a função `autenticarCliente`
```kotlin
private fun autenticarCliente(token: String?) {
        val usuarioToken: String = tokenService.getUsuario(token)//(5)
        val usuario: Usuario? = usuarioRepository.findByEmail(usuarioToken)
        val authentication = UsernamePasswordAuthenticationToken(usuario, null, usuario?.authorities)
        SecurityContextHolder 
        .getContext()
        .authentication = authentication
    }
```

7. Em `SecurityConfiguration` adicionar o filtro de autenticação
```kotlin
.and()
.addFilterBefore(AutenticacaoViaTokenFilter(//Rodar primeiro o nosso filtro
    tokenService = tokenService,
    usuarioRepository = usuarioRepository),
    UsernamePasswordAuthenticationFilter::class.java)
``` 

#### Profiles

mvn spring-boot:run

mvn spring-boot:run -D spring-boot.run.profiles=test


#### Testes de unidade

Dependencia do MockK
```xml
<dependency>
    <groupId>com.ninja-squad</groupId>
    <artifactId>springmockk</artifactId>
    <version>3.1.1</version>
    <scope>test</scope>
</dependency>
```
Dependencia do AssertJ
```xml
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.23.1</version>
    <scope>test</scope>
</dependency>
```
1. Teste do método listar

- Em TopicoServiceTest, preciso simular o mesmo comportamento do repository `repository.findByCursoNome(nomeCurso, paginacao)`
```kotlin
val topicoRepository: TopicoRepository = mockk {
        every { findByCursoNome(any(), any()) } //toda vez que o topicoRepository chamar o find... passando qualquer parâmetro any()
        returns topicos
    }
``` 
