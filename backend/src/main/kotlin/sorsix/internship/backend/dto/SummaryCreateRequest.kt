package sorsix.internship.backend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import sorsix.internship.backend.model.AthleteReport
import sorsix.internship.backend.model.Recommendation
import sorsix.internship.backend.model.Summary

data class SummaryCreateRequest(

    @field:NotNull(message = "Report ID is required")
    val reportId: Long?,

    @field:NotBlank
    val summarizedContent: String?
) {
    companion object {
        fun toEntity(dto: SummaryCreateRequest, report: AthleteReport): Summary {
            return Summary(
                summaryId = null,
                athleteReport = report,
                summarizedContent = dto.summarizedContent!!
            )
        }
    }
}