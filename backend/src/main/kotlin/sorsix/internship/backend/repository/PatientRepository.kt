package sorsix.internship.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import sorsix.internship.backend.model.Patient

@Repository
interface PatientRepository : JpaRepository<Patient, Long> {
    fun findByUserEmbg(embg: String): Patient?
    fun findByDoctorDoctorId(doctorId: Long, pageable: Pageable): Page<Patient>
    fun findByDoctorDoctorIdAndUserEmbgContaining(doctorId: Long, embg: String, pageable: Pageable): Page<Patient>
    @Query(
        """
        SELECT p
        FROM Patient p
        JOIN p.user u
        WHERE p.doctor IS NULL
          AND u.embg LIKE CONCAT('%', :embg, '%')
        """
    )
    fun findUnassignedUsers(embg: String, pageable: Pageable): Page<Patient>
}