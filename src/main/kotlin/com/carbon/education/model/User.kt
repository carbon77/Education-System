package com.carbon.education.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User (
    @Column(nullable = false, unique = true)
    var email: String? = null,

    @Column(nullable = false)
    var firstName: String? = null,

    @Column(nullable = true)
    var lastName: String? = null,

    @Column(nullable = false)
    var password: String? = null,

    @Enumerated(EnumType.STRING)
    var role: Role? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", nullable = false)
    var id: Long? = null
)