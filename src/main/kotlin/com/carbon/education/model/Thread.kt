package com.carbon.education.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.sql.Timestamp

@Entity(name = "threads")
data class Thread(
    @Column(name = "title", nullable = false)
    var title: String? = null,

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String? = null,

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
    @Column(name = "thread_id", nullable = false)
    var id: Long? = null,

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,

    @JsonIgnore
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var posts: List<Post> = ArrayList(),

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "banned_users",
        joinColumns = [JoinColumn(name = "thread_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var bannedUsers: MutableSet<User> = mutableSetOf()
) {
    @get:JsonInclude
    val author: Map<String, Any?>
        get() = mapOf(
            "id" to this.user?.id,
            "firstName" to this.user?.firstName,
            "lastName" to this.user?.lastName
        )

    @get:JsonInclude
    val bannedUsersIds: List<Long?>
        get() = bannedUsers.map { u -> u.id }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Thread

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title , user = $user )"
    }
}