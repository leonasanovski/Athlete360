package sorsix.internship.backend.service

import sorsix.internship.backend.dto.RecommendationCreateRequest
import sorsix.internship.backend.dto.RecommendationResponse

interface RecommendationService {
    fun findRecommendationsByDoctorId(doctorId: Long): List<RecommendationResponse>
    fun findRecommendationsByPatientId(patientId: Long): List<RecommendationResponse>
    fun findRecommendationsByReportId(reportId: Long): List<RecommendationResponse>
    fun create(recommendation: RecommendationCreateRequest) : Long
}