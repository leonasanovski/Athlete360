package sorsix.internship.backend.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import sorsix.internship.backend.model.enum.Gender
import sorsix.internship.backend.model.enum.SportsmanCategory
import java.time.LocalDate

@Entity
@Table(name = "patient")
data class Patient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    var patientId: Long? = null,

    @Column(name = "first_name", nullable = false)
    var firstName: String = "",

    @Column(name = "last_name", nullable = false)
    var lastName: String = "",

    @Column(name = "date_of_birth", nullable = false)
    var dateOfBirth: LocalDate = LocalDate.now(),

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    var gender: Gender = Gender.MALE,

    @Enumerated(EnumType.STRING)
    @Column(name = "sportsman_category", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    var sportsmanCategory: SportsmanCategory = SportsmanCategory.RECREATION,

    @Email
    @Column(nullable = false, unique = true)
    var email: String = ""
)