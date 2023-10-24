package com.carbon.education.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "materials")
data class Material(
    var description: String? = null,

    @Column(nullable = false)
    var link: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "material_id")
    var id: Long? = null
)
