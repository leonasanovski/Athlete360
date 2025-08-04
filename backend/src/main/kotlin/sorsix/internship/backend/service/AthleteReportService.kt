package sorsix.internship.backend.service

import sorsix.internship.backend.dto.AthleteReportCreateRequest
import sorsix.internship.backend.dto.AthleteReportResponse
import sorsix.internship.backend.dto.ReportMetricFlaggerDTO

interface AthleteReportService {
    fun create(requestObject: AthleteReportCreateRequest): Long
    fun findReportById(id: Long): AthleteReportResponse
    fun reportMetricsFlagging(reportId: Long): ReportMetricFlaggerDTO
    //TODO DELETE REPORT FROM DATABASE (ONLY THE DOCTOR CAN DO THIS)
    //TODO GET ALL RECOMMENDATIONS FOR A GIVEN REPORT ID
    //TODO LIST REPORTS BY PATIENT ID
    //TODO LIST REPORTS BY DOCTOR ID
    //TODO GET REPORT SUMMARY (GENERATED ONE)
    //TODO INTERESTING: AUTO FLAGGING WITH GREEN,YELLOW,RED FOR ALL THE METRICS
    /*
    When a doctor enters metrics (heart rate, body fat, blood pressure, etc.), the system
    highlights values that fall outside standard healthy ranges
    (based on athlete age/gender/category).
    */
}