package sorsix.internship.backend.service

import sorsix.internship.backend.dto.SummaryDTO

interface SummaryService {
    fun findSummaryByReportId(reportId: Long) : SummaryDTO
}