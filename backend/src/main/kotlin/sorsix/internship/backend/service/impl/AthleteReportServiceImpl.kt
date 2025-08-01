package sorsix.internship.backend.service.impl

import org.springframework.stereotype.Service
import sorsix.internship.backend.dto.AthleteReportCreateRequest
import sorsix.internship.backend.dto.AthleteReportResponse
import sorsix.internship.backend.model.AthleteReport
import sorsix.internship.backend.repository.AthleteReportRepository
import sorsix.internship.backend.repository.DoctorRepository
import sorsix.internship.backend.repository.PatientRepository
import sorsix.internship.backend.service.AthleteReportService

@Service
class AthleteReportServiceImpl(
    private val athleteReportRepository: AthleteReportRepository,
    private val doctorRepository: DoctorRepository,
    private val patientRepository: PatientRepository
) : AthleteReportService {

    override fun create(requestObject: AthleteReportCreateRequest): Long {
        val doctor = doctorRepository.findById(requestObject.doctorId!!)
            .orElseThrow { IllegalArgumentException("Doctor with id = ${requestObject.doctorId} not found") }

        val patient = patientRepository.findById(requestObject.patientId!!)
            .orElseThrow { IllegalArgumentException("Patient with id = ${requestObject.patientId} not found") }

        val report = AthleteReport(
            doctor = doctor,
            patient = patient,
            vo2Max = requestObject.vo2Max!!,
            restingHeartRate = requestObject.restingHeartRate!!,
            underPressureHeartRate = requestObject.underPressureHeartRate!!,
            bodyFatPercentage = requestObject.bodyFatPercentage!!,
            leanMuscleMass = requestObject.leanMuscleMass,
            boneDensity = requestObject.boneDensity!!,
            height = requestObject.height!!,
            weight = requestObject.weight!!,
            oneRepMaxBench = requestObject.oneRepMaxBench,
            oneRepMaxSquat = requestObject.oneRepMaxSquat,
            oneRepMaxDeadlift = requestObject.oneRepMaxDeadlift,
            jumpHeight = requestObject.jumpHeight,
            averageRunPerKilometer = requestObject.averageRunPerKilometer!!,
            shoulderFlexibility = requestObject.shoulderFlexibility,
            hipFlexibility = requestObject.hipFlexibility,
            balanceTime = requestObject.balanceTime!!,
            reactionTime = requestObject.reactionTime!!,
            coreStabilityScore = requestObject.coreStabilityScore!!,
            hemoglobin = requestObject.hemoglobin!!,
            glucose = requestObject.glucose!!,
            creatinine = requestObject.creatinine!!,
            vitaminD = requestObject.vitaminD!!,
            iron = requestObject.iron!!,
            testosterone = requestObject.testosterone!!,
            cortisol = requestObject.cortisol!!
        )
        return athleteReportRepository.save(report).reportId!!
    }

    override fun findReportById(id: Long): AthleteReportResponse {
        val report = athleteReportRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Athlete report with id = $id not found") }
        val doctor =
            doctorRepository.findById(id).orElseThrow { IllegalArgumentException("Doctor with id = $id not found") }
        val patient =
            patientRepository.findById(id).orElseThrow { IllegalArgumentException("Patient with id = $id not found") }

        return AthleteReportResponse(
            id = report.reportId,
            patient = "${patient.patientId} - ${patient.firstName} ${patient.lastName}",
            doctor = "${doctor.doctorId} - ${doctor.firstName} ${doctor.lastName}",
            vo2Max = report.vo2Max,
            restingHeartRate = report.restingHeartRate,
            underPressureHeartRate = report.underPressureHeartRate,
            bodyFatPercentage = report.bodyFatPercentage,
            leanMuscleMass = report.leanMuscleMass,
            boneDensity = report.boneDensity,
            height = report.height,
            weight = report.weight,
            oneRepMaxBench = report.oneRepMaxBench,
            oneRepMaxSquat = report.oneRepMaxSquat,
            oneRepMaxDeadlift = report.oneRepMaxDeadlift,
            jumpHeight = report.jumpHeight,
            averageRunPerKilometer = report.averageRunPerKilometer,
            shoulderFlexibility = report.shoulderFlexibility,
            hipFlexibility = report.hipFlexibility,
            balanceTime = report.balanceTime,
            reactionTime = report.reactionTime,
            coreStabilityScore = report.coreStabilityScore,
            hemoglobin = report.hemoglobin,
            glucose = report.glucose,
            creatinine = report.creatinine,
            vitaminD = report.vitaminD,
            iron = report.iron,
            testosterone = report.testosterone,
            cortisol = report.cortisol
        )
    }
}