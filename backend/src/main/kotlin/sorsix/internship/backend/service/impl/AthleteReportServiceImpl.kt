package sorsix.internship.backend.service.impl

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import sorsix.internship.backend.components.MetricsFlagHelper
import sorsix.internship.backend.dto.AthleteReportCreateRequest
import sorsix.internship.backend.dto.AthleteReportResponse
import sorsix.internship.backend.dto.AthleteReportShortDTO
import sorsix.internship.backend.dto.ReportMetricFlaggerDTO
import sorsix.internship.backend.mappers.toDto
import sorsix.internship.backend.model.AthleteReport
import sorsix.internship.backend.model.enum.Gender
import sorsix.internship.backend.repository.AthleteReportRepository
import sorsix.internship.backend.repository.DoctorRepository
import sorsix.internship.backend.repository.PatientRepository
import sorsix.internship.backend.service.AthleteReportService
import java.time.LocalDate
import java.time.Period

@Service
class AthleteReportServiceImpl(
    private val athleteReportRepository: AthleteReportRepository,
    private val doctorRepository: DoctorRepository,
    private val patientRepository: PatientRepository,
    private val metricsFlagHelper: MetricsFlagHelper
) : AthleteReportService {

    override fun create(requestObject: AthleteReportCreateRequest): Long {
        val doctor = doctorRepository.findById(requestObject.doctorId!!)
            .orElseThrow { IllegalArgumentException("Doctor with id = ${requestObject.doctorId} not found") }

        val patient = patientRepository.findById(requestObject.patientId!!)
            .orElseThrow { IllegalArgumentException("Patient with id = ${requestObject.patientId} not found") }

        val report = AthleteReport(
            doctor = doctor,
            patient = patient,
            status = requestObject.status,
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
        val report: AthleteReport = athleteReportRepository.findById(id)
            .orElseThrow { EntityNotFoundException("AthleteReport with id $id not found") }

        return report.toDto()
    }

    override fun reportMetricsFlagging(reportId: Long): ReportMetricFlaggerDTO {
        val report = athleteReportRepository.findById(reportId)
            .orElseThrow { NoSuchElementException("Report with id = $reportId not found") }
        val flagger = ReportMetricFlaggerDTO()
        flagger.vo2Max = metricsFlagHelper.flagVo2Max(
            report.vo2Max.toDouble(),
            Period.between(report.patient.dateOfBirth, LocalDate.now()).years,
            report.patient.gender == Gender.MALE
        )
        flagger.restingHeartRate = metricsFlagHelper.flagRestingHeartRate(report.restingHeartRate)
        flagger.underPressureHeartRate = metricsFlagHelper.flagUnderPressureHeartRate(
            report.underPressureHeartRate,
            Period.between(report.patient.dateOfBirth, LocalDate.now()).years
        )
        flagger.leanMuscleMass = report.leanMuscleMass?.let {
            metricsFlagHelper.flagLeanMass(report.weight, it)
        }
        flagger.bodyFatPercentage = metricsFlagHelper.flagBodyFat(report.bodyFatPercentage)
        flagger.boneDensity = metricsFlagHelper.flagBoneDensity(report.boneDensity)
        flagger.bmi = metricsFlagHelper.flagBMI(report.weight.toDouble(), report.height.toDouble())
        flagger.hemoglobin = metricsFlagHelper.flagHemoglobin(report.hemoglobin, report.patient.gender == Gender.MALE)
        flagger.glucose = metricsFlagHelper.flagGlucose(report.glucose)
        flagger.vitaminD = metricsFlagHelper.flagVitaminD(report.vitaminD)
        flagger.iron = metricsFlagHelper.flagIron(report.iron)
        flagger.testosterone = metricsFlagHelper.flagTestosterone(report.testosterone)
        flagger.cortisol = metricsFlagHelper.flagCortisol(report.cortisol)
        return flagger
    }

    override fun getReportsShortByPatientId(patientId: Long): List<AthleteReportShortDTO> =
        athleteReportRepository
            .findByPatientPatientId(patientId)
            .map { report ->
                AthleteReportShortDTO(
                    id         = report.reportId!!,
                    createdAt  = report.createdAt,
                    doctorName = "${report.doctor.firstName} ${report.doctor.lastName}",
                    patientName = "${report.patient.firstName} ${report.patient.lastName}",
                    status     = report.status,
                    vo2Max     = report.vo2Max
                )
            }

    override fun getReportsShortByDoctorId(doctorId: Long): List<AthleteReportShortDTO> =
        athleteReportRepository
            .findByDoctorDoctorId(doctorId)
            .map { report ->
                AthleteReportShortDTO(
                    id         = report.reportId!!,
                    createdAt  = report.createdAt,
                    doctorName = "${report.doctor.firstName} ${report.doctor.lastName}",
                    patientName = "${report.patient.firstName} ${report.patient.lastName}",
                    status     = report.status,
                    vo2Max     = report.vo2Max
                )
            }

    override fun findLatestReportId(): Long =
        athleteReportRepository.findTopByOrderByReportIdDesc()?.reportId
            ?: throw NoSuchElementException("No reports found.")
}