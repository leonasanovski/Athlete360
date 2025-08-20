package sorsix.internship.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import sorsix.internship.backend.model.Mood

@Repository
interface MoodRepository : JpaRepository<Mood, Long>, JpaSpecificationExecutor<Mood> {
    fun findByPatientPatientId(patientId: Long): List<Mood>
    fun findByMoodId(id: Long): Mood?

}