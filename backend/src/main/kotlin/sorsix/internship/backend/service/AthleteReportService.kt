package sorsix.internship.backend.service

import sorsix.internship.backend.dto.AthleteReportCreateRequest
import sorsix.internship.backend.dto.AthleteReportResponse
import sorsix.internship.backend.dto.AthleteReportShortDTO
import sorsix.internship.backend.dto.ReportMetricFlaggerDTO

interface AthleteReportService {
    fun create(requestObject: AthleteReportCreateRequest): Long
    fun findReportById(id: Long): AthleteReportResponse
    fun reportMetricsFlagging(reportId: Long): ReportMetricFlaggerDTO
    //TODO DELETE REPORT FROM DATABASE (ONLY THE DOCTOR CAN DO THIS)
    //TODO LIST REPORTS BY PATIENT ID
    fun getReportsShortByPatientId(patientId: Long): List<AthleteReportShortDTO>
    //TODO LIST REPORTS BY DOCTOR ID
    //TODO GET REPORT SUMMARY (GENERATED ONE)
}