package sorsix.internship.backend.mappers

import sorsix.internship.backend.dto.SummaryDTO
import sorsix.internship.backend.model.Summary

fun Summary.toDto(): SummaryDTO = SummaryDTO(
    summaryId = summaryId,
    reportId = athleteReport.reportId!!,
    summarizedContent = summarizedContent
)