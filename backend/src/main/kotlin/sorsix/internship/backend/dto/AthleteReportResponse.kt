package sorsix.internship.backend.dto

import jakarta.validation.constraints.NotNull
import sorsix.internship.backend.model.Doctor
import sorsix.internship.backend.model.Patient
import sorsix.internship.backend.model.enum.AthleteReportStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class AthleteReportResponse(
    val reportId: Long?,
    val doctor: String,
    val patient: String,
    val createdAt: LocalDateTime,
    val status: AthleteReportStatus,
    val vo2Max: BigDecimal,
    val restingHeartRate: Int,
    val underPressureHeartRate: Int,
    val bodyFatPercentage: BigDecimal,
    val leanMuscleMass: BigDecimal?,
    val boneDensity: BigDecimal,
    val height: BigDecimal,
    val weight: BigDecimal,
    val oneRepMaxBench: BigDecimal?,
    val oneRepMaxSquat: BigDecimal?,
    val oneRepMaxDeadlift: BigDecimal?,
    val jumpHeight: BigDecimal?,
    val averageRunPerKilometer: BigDecimal,
    val shoulderFlexibility: Int?,
    val hipFlexibility: Int?,
    val balanceTime: BigDecimal,
    val reactionTime: BigDecimal,
    val coreStabilityScore: Int,
    val hemoglobin: BigDecimal,
    val glucose: BigDecimal,
    val creatinine: BigDecimal,
    val vitaminD: BigDecimal,
    val iron: BigDecimal,
    val testosterone: BigDecimal,
    val cortisol: BigDecimal
)
