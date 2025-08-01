package sorsix.internship.backend.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import sorsix.internship.backend.model.enum.Gender
import sorsix.internship.backend.model.enum.SportsmanCategory
import java.time.LocalDate

data class PatientCreateRequest (

    @field:NotNull(message = "Doctor ID is required")
    val doctorId: Long?,

    @field:NotBlank(message = "First name is required")
    val firstName: String?,

    @field:NotBlank(message = "Last name is required")
    val lastName: String?,

    @field:NotNull(message = "Date of birth is required")
    val dateOfBirth: LocalDate?,

    @field:NotNull(message = "Gender is required")
    val gender: Gender?,

    @field:NotNull(message = "Category is required")
    val sportsmanCategory: SportsmanCategory?,

    @field:Email(message = "Must be a valid email")
    @field:NotBlank(message = "Email is required")
    val email: String?
)