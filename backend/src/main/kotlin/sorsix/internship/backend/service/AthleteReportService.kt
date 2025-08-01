package sorsix.internship.backend.service

import sorsix.internship.backend.dto.AthleteReportCreateRequest
import sorsix.internship.backend.dto.AthleteReportResponse
import sorsix.internship.backend.model.AthleteReport

interface AthleteReportService {
    fun create(requestObject: AthleteReportCreateRequest): Long
    fun findReportById(id: Long): AthleteReportResponse
}