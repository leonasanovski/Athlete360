package sorsix.internship.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import sorsix.internship.backend.dto.AthleteReportFormDTO
import sorsix.internship.backend.dto.AthleteReportResponse
import sorsix.internship.backend.dto.AthleteReportShortDTO
import sorsix.internship.backend.dto.ReportMetricFlaggerDTO

interface AthleteReportService {
    fun create(requestObject: AthleteReportFormDTO): Long
    fun findReportById(id: Long): AthleteReportResponse
    fun reportMetricsFlagging(reportId: Long): ReportMetricFlaggerDTO
    //TODO DELETE REPORT FROM DATABASE (ONLY THE DOCTOR CAN DO THIS)
    fun getReportsShortByPatientId(patientId: Long, pageable: Pageable): Page<AthleteReportShortDTO>
    fun getReportsShortByDoctorId(doctorId: Long, pageable: Pageable): Page<AthleteReportShortDTO>
    //fun findLatestReportId(): Long
    fun findLatestReportIdByPatientId(patientId: Long): Long
    //TODO GET REPORT SUMMARY (GENERATED ONE)
}