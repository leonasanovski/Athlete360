package sorsix.internship.backend.dto

import sorsix.internship.backend.model.AthleteReport
import sorsix.internship.backend.model.Doctor
import sorsix.internship.backend.model.Patient
import sorsix.internship.backend.model.enum.AthleteReportStatus
import java.math.BigDecimal

data class AthleteReportFormDTO (
    val doctorId: Long,
    val embg: String,
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
) {
    companion object {
        fun toEntity(dto: AthleteReportFormDTO, doctor: Doctor, patient: Patient): AthleteReport {
            return AthleteReport(
                doctor = doctor,
                patient = patient,
                status = dto.status,
                vo2Max = dto.vo2Max,
                restingHeartRate = dto.restingHeartRate,
                underPressureHeartRate = dto.underPressureHeartRate,
                bodyFatPercentage = dto.bodyFatPercentage,
                leanMuscleMass = dto.leanMuscleMass,
                boneDensity = dto.boneDensity,
                height = dto.height,
                weight = dto.weight,
                oneRepMaxBench = dto.oneRepMaxBench,
                oneRepMaxSquat = dto.oneRepMaxSquat,
                oneRepMaxDeadlift = dto.oneRepMaxDeadlift,
                jumpHeight = dto.jumpHeight,
                averageRunPerKilometer = dto.averageRunPerKilometer,
                shoulderFlexibility = dto.shoulderFlexibility,
                hipFlexibility = dto.hipFlexibility,
                balanceTime = dto.balanceTime,
                reactionTime = dto.reactionTime,
                coreStabilityScore = dto.coreStabilityScore,
                hemoglobin = dto.hemoglobin,
                glucose = dto.glucose,
                creatinine = dto.creatinine,
                vitaminD = dto.vitaminD,
                iron = dto.iron,
                testosterone = dto.testosterone,
                cortisol = dto.cortisol
            )
        }
    }
}
