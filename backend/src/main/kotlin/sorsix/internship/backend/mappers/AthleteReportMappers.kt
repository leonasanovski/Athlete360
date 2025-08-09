package sorsix.internship.backend.mappers

import sorsix.internship.backend.dto.AthleteReportResponse
import sorsix.internship.backend.model.AthleteReport

fun AthleteReport.toDto(): AthleteReportResponse = AthleteReportResponse(
    reportId             = reportId,
    doctor               = "${doctor.firstName} ${doctor.lastName}",
    patient              = "${patient.firstName} ${patient.lastName}",
    embg                 = patient.embg,
    createdAt            = createdAt,
    status               = status,
    vo2Max               = vo2Max,
    restingHeartRate     = restingHeartRate,
    underPressureHeartRate = underPressureHeartRate,
    bodyFatPercentage    = bodyFatPercentage,
    leanMuscleMass       = leanMuscleMass,
    boneDensity          = boneDensity,
    height               = height,
    weight               = weight,
    oneRepMaxBench       = oneRepMaxBench,
    oneRepMaxSquat       = oneRepMaxSquat,
    oneRepMaxDeadlift    = oneRepMaxDeadlift,
    jumpHeight           = jumpHeight,
    averageRunPerKilometer = averageRunPerKilometer,
    shoulderFlexibility  = shoulderFlexibility,
    hipFlexibility       = hipFlexibility,
    balanceTime          = balanceTime,
    reactionTime         = reactionTime,
    coreStabilityScore   = coreStabilityScore,
    hemoglobin           = hemoglobin,
    glucose              = glucose,
    creatinine           = creatinine,
    vitaminD             = vitaminD,
    iron                 = iron,
    testosterone         = testosterone,
    cortisol             = cortisol
)