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

3. Criação do `AutenticacaoController`

   1. Criação do método `autenticar(@RequestBody @Valid form: LoginForm): ResponseEntity<?>`
    - O objetivo deste método é pegar o login e a senha, autenticar no sistema(verificar no banco) e estando tudo ok eu gero o token;
   2. Criação da classe DTO `LoginForm`;
   3. Liberar o caminho `/auth` em SecurityConfigurations: ` .antMatchers("/auth").permitAll()`
   
#### No Spring, não é possível injetar automaticamente o `AuthenticationManager`
   
1. Em `SecurityConfiguration` criar o método `authenticationManager` e em seguida, injetar no construtor do `Controller`;
```kotlin
@Bean
fun authenticationManager(auth: AuthenticationConfiguration): AuthenticationManager {
    return auth.authenticationManager
}
```

#### Em `AutenticacaoController`

1. Injeto o `AuthenticationManager` no construtor;
```kotlin
private val authManager: AuthenticationManager
```
2. Criar a função `converter` na classe `LoginForm`;
```kotlin
fun converter(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, senha)//Uma implementação projetada para apresentação simples de um nome de usuário e senha
    }
``` 
3. No método `autenticar`    
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
@Value("\${forum.jwt.expiration}")
lateinit var expiration: String

@Value("\${forum.jwt.secret}")
lateinit var secret: String

fun gerarToken(authentication: Authentication): String {
    //Recuperar o usuário que esta logado;
    //Ele devolve um Object e com isso, é necessário o cast para DetalhesUsuario;
    val usuarioLogado: DetalhesUsuario = authentication.principal as DetalhesUsuario
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

3. Em `AutenticacaoController`, no método `autenticar`
```kotlin
val token = tokenService.gerarToken(authentication)
``` 

#### Retornando o token para o cliente

1. Criação do `TokenDto`;
2. `return ResponseEntity.ok(TokenDto(token, "Bearer"))` devolvo um objeto(token) e informo o tipo de autenticação no corpo da resposta;

#### Recuperando o token do header Authorization

1. Criação da classe `AutenticacaoViaTokenFilter(): OncePerRequestFilter()`;
2. Implementar o método `doFilterInternal()`;

   

