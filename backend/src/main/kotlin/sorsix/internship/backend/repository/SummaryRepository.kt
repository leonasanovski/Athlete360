package sorsix.internship.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.internship.backend.model.Summary

@Repository
interface SummaryRepository : JpaRepository<Summary, Long> {
    fun findByAthleteReportReportId(reportId: Long): Summary?
}