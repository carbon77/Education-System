package com.carbon.education.model

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity(name = "lessons")
data class Lesson(
    @Column(nullable = false)
    var title: String? = null,

    @Column(nullable = false)
    var order: Int? = null,

    @Column(nullable = false, columnDefinition = "TEXT")
    var text: String? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    var course: Course? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "lesson_id")
    var id: Long? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Lesson

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title )"
    }
}