package sorsix.internship.backend.service.impl

import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import sorsix.internship.backend.model.AthleteReport
import sorsix.internship.backend.model.Recommendation
import sorsix.internship.backend.repository.AthleteReportRepository
import sorsix.internship.backend.repository.DoctorRepository
import sorsix.internship.backend.repository.PatientRepository
import sorsix.internship.backend.repository.RecommendationRepository
import sorsix.internship.backend.service.AthleteReportService
import sorsix.internship.backend.service.RecommendationService

@Service
class RecommendationServiceImpl(
    private val recommendationRepository: RecommendationRepository,
    private val doctorRepository: DoctorRepository,
    private val patientRepository: PatientRepository,
    private val athleteReportRepository: AthleteReportRepository
) : RecommendationService {
    override fun findRecommendationsByDoctorId(doctorId: Long): List<Recommendation> {
        //CHECK IF DOCTOR EXISTS
        val doctor = doctorRepository.findById(doctorId)
            .orElseThrow { throw NoSuchElementException("There is no doctor with id=$doctorId in the database.") }
        return recommendationRepository.findByReportDoctorDoctorId(doctorId)
    }

    override fun findRecommendationsByPatientId(patientId: Long): List<Recommendation> {
        val patient = patientRepository.findById(patientId)
            .orElseThrow { throw NoSuchElementException("There is no patient with id=$patientId in the database.") }
        return recommendationRepository.findByReportPatientPatientId(patientId)
    }
}