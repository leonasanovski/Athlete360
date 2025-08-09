package sorsix.internship.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.internship.backend.model.AthleteReport

@Repository
interface AthleteReportRepository : JpaRepository<AthleteReport, Long> {
    fun findByPatientPatientId(patientId: Long, pageable: Pageable): Page<AthleteReport>

    fun findByDoctorDoctorId(doctorId: Long, pageable: Pageable): Page<AthleteReport>

    fun findByPatientPatientIdOrderByCreatedAtDesc(patientId: Long): List<AthleteReport>

    fun findTopByPatientPatientIdOrderByReportIdDesc(patientId: Long): AthleteReport?


}
