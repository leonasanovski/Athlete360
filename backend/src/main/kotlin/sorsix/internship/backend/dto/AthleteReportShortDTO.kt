package sorsix.internship.backend.dto

import sorsix.internship.backend.model.Doctor
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
)