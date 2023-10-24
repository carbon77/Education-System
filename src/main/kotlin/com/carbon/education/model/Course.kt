package com.carbon.education.model

import jakarta.persistence.*
import org.hibernate.Hibernate
import java.sql.Timestamp

@Entity(name = "courses")
data class Course(
    @Column(nullable = false)
    var name: String? = null,

    @Column(columnDefinition = "TEXT")
    var text: String? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,

    @Column(
        name = "created_at",
        nullable = false,
        insertable = false,
        updatable = false,
        columnDefinition = "TIMESTAMP DEFAULT now()"
    )
    var createdAt: Timestamp? = null,

    @ManyToMany
    @JoinTable(
        name = "course_registrations",
        joinColumns = [JoinColumn(name = "course_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var students: MutableSet<User> = mutableSetOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "course_id")
    var id: Long? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Course

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , user = $user )"
    }
}