package sorsix.internship.backend.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class DoctorCreateRequest(

    @field:NotBlank(message = "First name is required")
    val firstName: String?,

    @field:NotBlank(message = "Last name is required")
    val lastName: String?,

    @field:NotBlank(message = "Specialization is required")
    val specialization: String?,

    @field:Email(message = "Must be a valid email")
    @field:NotBlank(message = "Email is required")
    val email: String?
)
