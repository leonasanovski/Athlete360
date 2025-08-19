package sorsix.internship.backend.security.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime

enum class UserRole {
    PATIENT,
    DOCTOR,
    ADMIN,
    PENDING
}

@Entity
@Table(name = "app_user",
    uniqueConstraints = [
        UniqueConstraint(name = "ux_app_user_embg", columnNames = ["embg"]),
        UniqueConstraint(name = "ux_app_user_email", columnNames = ["email"])
    ])
data class AppUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    val userId: Long? = null,

    @Column(name = "first_name", nullable = false, length = 100)
    var firstName: String,

    @Column(name = "last_name", nullable = false, length = 100)
    var lastName: String,

    @Column(name = "embg", nullable = false, length = 13, updatable = false)
    var embg: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    var role: UserRole = UserRole.PENDING,

    @Column(name = "password_hash", nullable = false)
    var passwordHash: String,

    @Column(name = "email", nullable = true, length = 150)
    var email: String? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null
)