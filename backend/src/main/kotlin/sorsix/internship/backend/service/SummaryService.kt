package sorsix.internship.backend.service

import sorsix.internship.backend.dto.SummaryCreateRequest
import sorsix.internship.backend.dto.SummaryDTO
import sorsix.internship.backend.model.AthleteReport

interface SummaryService {
    fun findSummaryByReportId(reportId: Long) : SummaryDTO
    fun create(summary: SummaryCreateRequest) : Long
    fun createWithAI(reportId: Long): Long
    fun update(reportId: Long, request: SummaryCreateRequest): Long
}