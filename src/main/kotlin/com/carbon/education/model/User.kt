package com.carbon.education.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User(
    @Column(nullable = false, unique = true)
    var email: String? = null,

    @Column(nullable = false)
    var firstName: String? = null,

    @Column(nullable = true)
    var lastName: String? = null,

    @JsonIgnore
    @Column(nullable = false)
    private var password: String? = null,

    @Enumerated(EnumType.STRING)
    var role: Role? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", nullable = false)
    var id: Long? = null
) : UserDetails {

    override fun getAuthorities(): List<GrantedAuthority> = role!!.getAuthorities()

    override fun getPassword(): String? = password
    override fun getUsername(): String? = email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , email = $email , firstName = $firstName , lastName = $lastName )"
    }
}