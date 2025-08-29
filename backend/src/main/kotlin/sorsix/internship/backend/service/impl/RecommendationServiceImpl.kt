package sorsix.internship.backend.service.impl

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import sorsix.internship.backend.dto.RecommendationCreateRequest
import sorsix.internship.backend.dto.RecommendationResponse
import sorsix.internship.backend.mappers.toDto
import sorsix.internship.backend.repository.AthleteReportRepository
import sorsix.internship.backend.repository.DoctorRepository
import sorsix.internship.backend.repository.PatientRepository
import sorsix.internship.backend.repository.RecommendationRepository
import sorsix.internship.backend.service.RecommendationService

@Service
class RecommendationServiceImpl(
    private val recommendationRepository: RecommendationRepository,
    private val doctorRepository: DoctorRepository,
    private val patientRepository: PatientRepository,
    private val athleteReportRepository: AthleteReportRepository
) : RecommendationService {
    override fun findRecommendationsByDoctorId(doctorId: Long): List<RecommendationResponse> {
        val doctor = doctorRepository.findById(doctorId)
            .orElseThrow { throw EntityNotFoundException("There is no doctor with id=$doctorId in the database.") }
        return recommendationRepository.findByReportDoctorDoctorId(doctorId).map { recommendation ->
            recommendation.toDto()
        };
    }

    override fun findRecommendationsByPatientId(patientId: Long): List<RecommendationResponse> {
        val patient = patientRepository.findById(patientId)
            .orElseThrow { throw EntityNotFoundException("There is no patient with id=$patientId in the database.") }
        return recommendationRepository.findByReportPatientPatientId(patientId).map { recommendation ->
            recommendation.toDto()
        };
    }

    override fun findRecommendationsByReportId(reportId: Long): List<RecommendationResponse> {
        val report = athleteReportRepository.findById(reportId)
            .orElseThrow { throw EntityNotFoundException("There is no report with id=$reportId in the database.") }
        return recommendationRepository.findByReportReportId(reportId).map { recommendation ->
            recommendation.toDto()
        };
    }

    override fun create(recommendation: RecommendationCreateRequest): Long {
        val report = athleteReportRepository.findById(recommendation.reportId!!)
            .orElseThrow { EntityNotFoundException("Report with id = ${recommendation.reportId} not found") }
        val res = RecommendationCreateRequest.toEntity(recommendation, report);
        recommendationRepository.save(res);

        return report.reportId!!;
    }
}