package sorsix.internship.backend.dto

import sorsix.internship.backend.model.AthleteReport
import sorsix.internship.backend.model.enum.AthleteReportStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class AthleteReportShortDTO (
    val id: Long,
    val createdAt: LocalDateTime,
    val doctorName: String,
    val patientName: String,
    val status: AthleteReportStatus,
    val vo2Max: BigDecimal
) {
    companion object {
        fun fromEntity(report: AthleteReport): AthleteReportShortDTO {
            return AthleteReportShortDTO(
                id         = report.reportId!!,
                createdAt  = report.createdAt,
                doctorName = "${report.doctor.firstName} ${report.doctor.lastName}",
                patientName = "${report.patient.firstName} ${report.patient.lastName}",
                status     = report.status,
                vo2Max     = report.vo2Max
            )
        }
    }
}