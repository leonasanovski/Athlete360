package sorsix.internship.backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email

@Entity
@Table(name = "doctor")
data class Doctor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id", nullable = false)
    val doctorId: Long? = null,

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    val lastName: String,

    @Column(name = "specialization", nullable = false)
    val specialization: String,

    @Email
    @Column(nullable = false, unique = true)
    var email: String
)