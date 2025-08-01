package sorsix.internship.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.internship.backend.model.Recommendation

@Repository
interface RecommendationRepository : JpaRepository<Recommendation, Long> {
    fun findByReportReportId(reportId: Long): List<Recommendation>
}