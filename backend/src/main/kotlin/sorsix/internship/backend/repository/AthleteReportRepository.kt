package sorsix.internship.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.internship.backend.model.AthleteReport

@Repository
interface AthleteReportRepository : JpaRepository<AthleteReport, Long> {
    //fun findByReportId(reportId: Long?): AthleteReport?
    fun findByPatientPatientId(patientId: Long): List<AthleteReport>
    fun findByDoctorDoctorId(doctorId: Long): List<AthleteReport>
}