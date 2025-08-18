package sorsix.internship.backend.service

import sorsix.internship.backend.dto.SummaryCreateRequest
import sorsix.internship.backend.dto.SummaryDTO

interface SummaryService {
    fun findSummaryByReportId(reportId: Long) : SummaryDTO
    fun create(summary: SummaryCreateRequest) : Long
    fun getSummaryAI(reportId: Long): String
    fun update(reportId: Long, request: SummaryCreateRequest): Long
}