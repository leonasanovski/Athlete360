package sorsix.internship.backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email

@Entity
@Table(name = "entity")
data class Doctor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id", nullable = false)
    val doctorId: Long? = null,

    @Column(name = "doctor_name", nullable = false)
    val doctorName: String,

    @Column(name = "doctor_surname", nullable = false)
    val doctorSurname: String,

    @Column(name = "specialization", nullable = false)
    val specialization: String,

    @Email
    @Column(nullable = false, unique = true)
    var email: String
)
