package sorsix.internship.backend.dto

data class SummaryDTO(
    val summaryId: Long?,
    val reportId: Long,
    val summarizedContent: String
)
