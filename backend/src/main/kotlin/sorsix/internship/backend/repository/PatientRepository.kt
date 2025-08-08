package sorsix.internship.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.internship.backend.model.Patient

@Repository
interface PatientRepository : JpaRepository<Patient, Long> {
    fun findByDoctorDoctorId(doctorId: Long, pageable: Pageable): Page<Patient>
}