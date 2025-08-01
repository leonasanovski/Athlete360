package sorsix.internship.backend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SummaryCreateRequest(

    @field:NotNull(message = "Report ID is required")
    val reportId: Long?,

    @field:NotBlank
    val summarizedContent: String?
)