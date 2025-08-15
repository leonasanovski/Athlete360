package sorsix.internship.backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import sorsix.internship.backend.model.enum.Gender
import sorsix.internship.backend.model.enum.SportsmanCategory
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "patient")
data class Patient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    val patientId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    val doctor: Doctor,

    @Column(name = "embg", nullable = false)
    val embg: String,

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    val lastName: String,

    @Column(name = "date_of_birth", nullable = false)
    val dateOfBirth: LocalDate = LocalDate.now(),

    @Column(name = "date_of_latest_checkup")
    var dateOfLatestCheckUp: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    val gender: Gender = Gender.MALE,

    @Enumerated(EnumType.STRING)
    @Column(name = "sportsman_category", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    val sportsmanCategory: SportsmanCategory = SportsmanCategory.RECREATION,

    @Email
    @Column(nullable = false, unique = true)
    val email: String
)