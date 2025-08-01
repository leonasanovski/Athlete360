package sorsix.internship.backend.dto

import jakarta.validation.constraints.NotNull
import sorsix.internship.backend.model.Doctor
import sorsix.internship.backend.model.Patient
import java.math.BigDecimal

data class AthleteReportResponse(
    @field:NotNull
    val id: Long?,

    @field:NotNull(message = "Doctor ID is required")
    val doctor: String?,

    @field:NotNull(message = "Patient ID is required")
    val patient: String?,

    @field:NotNull
    val vo2Max: BigDecimal?,

    @field:NotNull
    val restingHeartRate: Int?,

    @field:NotNull
    val underPressureHeartRate: Int?,

    @field:NotNull
    val bodyFatPercentage: BigDecimal?,

    val leanMuscleMass: BigDecimal?,

    @field:NotNull
    val boneDensity: BigDecimal?,

    @field:NotNull
    val height: BigDecimal?,

    @field:NotNull
    val weight: BigDecimal?,

    val oneRepMaxBench: BigDecimal?,

    val oneRepMaxSquat: BigDecimal?,

    val oneRepMaxDeadlift: BigDecimal?,

    val jumpHeight: BigDecimal?,

    @field:NotNull
    val averageRunPerKilometer: BigDecimal?,

    val shoulderFlexibility: Int?,

    val hipFlexibility: Int?,

    @field:NotNull
    val balanceTime: BigDecimal?,

    @field:NotNull
    val reactionTime: BigDecimal?,

    @field:NotNull
    val coreStabilityScore: Int?,

    @field:NotNull
    val hemoglobin: BigDecimal?,

    @field:NotNull
    val glucose: BigDecimal?,

    @field:NotNull
    val creatinine: BigDecimal?,

    @field:NotNull
    val vitaminD: BigDecimal?,

    @field:NotNull
    val iron: BigDecimal?,

    @field:NotNull
    val testosterone: BigDecimal?,

    @field:NotNull
    val cortisol: BigDecimal?
)
