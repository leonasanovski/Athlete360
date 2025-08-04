package sorsix.internship.backend.service

import sorsix.internship.backend.model.Recommendation

interface RecommendationService {
    //todo filtering recommendation by TYPE
    //todo filtering recommendation by REPORT ID
    //todo filtering recommendation by TARGET GOAL STRING
    //todo delete recommendation from a given report with id = N
    //todo add recommendation to an existing report (make some ping to the dashboard)
    fun findRecommendationsByDoctorId(doctorId: Long): List<Recommendation>
    fun findRecommendationsByPatientId(patientId: Long): List<Recommendation>
}