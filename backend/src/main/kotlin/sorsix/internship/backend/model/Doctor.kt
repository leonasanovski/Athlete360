package sorsix.internship.backend.model

import jakarta.persistence.*
import sorsix.internship.backend.security.model.AppUser

@Entity
@Table(name = "doctor")
data class Doctor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id", nullable = false)
    val doctorId: Long? = null,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    val user: AppUser,

    @Column(name = "specialization", nullable = false, length = 100)
    val specialization: String
)