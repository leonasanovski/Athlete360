package sorsix.internship.backend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import sorsix.internship.backend.model.enum.RecommendationType
import sorsix.internship.backend.model.enum.RestrictionLevel

data class RecommendationCreateRequest(

    @field:NotNull(message = "Report ID is required")
    val reportId: Long?,

    @field:NotNull
    val type: RecommendationType?,

    @field:NotNull
    val restrictionLevel: RestrictionLevel?,

    @field:NotBlank
    val label: String?,

    @field:NotBlank
    val description: String?,

    @field:NotNull
    val costPerMonth: Int?,

    @field:NotNull
    val durationWeeks: Int?,

    @field:NotNull
    val frequencyPerDay: Int?,

    @field:NotBlank
    val targetGoal: String?,

    @field:NotNull
    val effectivenessRating: Int?,

    @field:NotBlank
    val doctorPersonalizedNotes: String?
)
