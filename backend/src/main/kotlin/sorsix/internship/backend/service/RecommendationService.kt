package sorsix.internship.backend.service

import sorsix.internship.backend.dto.RecommendationCreateRequest
import sorsix.internship.backend.dto.RecommendationResponse

interface RecommendationService {
    //todo filtering recommendation by TYPE
    //todo filtering recommendation by REPORT ID
    //todo filtering recommendation by TARGET GOAL STRING
    //todo delete recommendation from a given report with id = N
    //todo add recommendation to an existing report (make some ping to the dashboard)
    fun findRecommendationsByDoctorId(doctorId: Long): List<RecommendationResponse>
    fun findRecommendationsByPatientId(patientId: Long): List<RecommendationResponse>
    fun findRecommendationsByReportId(reportId: Long): List<RecommendationResponse>
    fun create(recommendation: RecommendationCreateRequest) : Long
}