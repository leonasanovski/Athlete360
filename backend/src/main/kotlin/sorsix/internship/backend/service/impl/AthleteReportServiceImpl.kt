package sorsix.internship.backend.service.impl

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import sorsix.internship.backend.components.MetricsFlagHelper
import sorsix.internship.backend.dto.AthleteReportFormDTO
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
import java.time.LocalDateTime
import java.time.Period

@Service
class AthleteReportServiceImpl(
    private val athleteReportRepository: AthleteReportRepository,
    private val doctorRepository: DoctorRepository,
    private val patientRepository: PatientRepository,
    private val metricsFlagHelper: MetricsFlagHelper
) : AthleteReportService {

    override fun create(requestObject: AthleteReportFormDTO): Long {
        val doctor = doctorRepository.findById(requestObject.doctorId)
            .orElseThrow { IllegalArgumentException("Doctor with id = ${requestObject.doctorId} not found") }

        val patient = patientRepository.findByEmbg(requestObject.embg)
            ?: throw IllegalArgumentException("Patient with embg = ${requestObject.embg} not found")

        patient.dateOfLatestCheckUp = LocalDateTime.now();
        patientRepository.save(patient);

        val report = AthleteReportFormDTO.toEntity(requestObject, doctor, patient)
        return athleteReportRepository.save(report).reportId!!
    }

    override fun findReportById(id: Long): AthleteReportResponse {
        val report: AthleteReport = athleteReportRepository.findById(id)
            .orElseThrow { EntityNotFoundException("AthleteReport with id $id not found") }

        return report.toDto();
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

    override fun getReportsShortByPatientId(patientId: Long, pageable: Pageable): Page<AthleteReportShortDTO> =
        athleteReportRepository
            .findByPatientPatientId(patientId, pageable)
            .map { AthleteReportShortDTO.fromEntity(it) }

    override fun getReportsShortByDoctorId(doctorId: Long, pageable: Pageable): Page<AthleteReportShortDTO> =
        athleteReportRepository
            .findByDoctorDoctorId(doctorId, pageable)
            .map { AthleteReportShortDTO.fromEntity(it) }


    override fun findLatestReportIdByPatientId(patientId: Long): Long =
        athleteReportRepository.findTopByPatientPatientIdOrderByReportIdDesc(patientId)?.reportId
            ?: throw NoSuchElementException("No reports found.")

}