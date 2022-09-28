package br.com.crystaldata.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val nome: String,
    val email: String,
    val senha: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_role",
        joinColumns = [JoinColumn(name = "usuario_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    @JsonIgnore
    val roles: List<Role> = mutableListOf()
): UserDetails {

    override fun getAuthorities() = roles

    override fun getPassword() = senha

    override fun getUsername() = email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled(): Boolean = true
}