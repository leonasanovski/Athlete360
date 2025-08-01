package sorsix.internship.backend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class MoodCreateRequest(

    @field:NotNull(message = "Patient ID is required")
    val patientId: Long?,

    @field:NotBlank(message = "Mood level is required")
    val moodLevel: String?,

    @field:NotBlank(message = "Mood description is required")
    val moodDescription: String?
)
