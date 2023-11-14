package com.carbon.education.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.sql.Timestamp

@Entity(name = "posts")
data class Post(
    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    var text: String? = null,

    @Column(
        name = "created_at",
        nullable = false,
        insertable = false,
        updatable = false,
        columnDefinition = "TIMESTAMP DEFAULT now()"
    )
    var createdAt: Timestamp? = null,

    @Id
    @GeneratedValue
    @Column(name = "post_id", nullable = false)
    var id: Long? = null,

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "thread_id", nullable = false)
    var thread: Thread? = null,

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null
) {
    @get:JsonInclude
    val threadId: Long? get() = this.thread?.id

    @get:JsonInclude
    val author: Map<String, Any?>
        get() = mapOf(
            "id" to this.user?.id,
            "firstName" to this.user?.firstName,
            "lastName" to this.user?.lastName
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Post

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , thread = $thread , user = $user )"
    }
}