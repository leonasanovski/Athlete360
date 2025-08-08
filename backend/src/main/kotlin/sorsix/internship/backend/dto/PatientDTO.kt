package sorsix.internship.backend.dto

import sorsix.internship.backend.model.Patient
import sorsix.internship.backend.model.enum.Gender
import sorsix.internship.backend.model.enum.SportsmanCategory
import java.time.LocalDate
import java.time.LocalDateTime

data class PatientDTO(
    val patientId: Long,
    val name: String,
    val dateOfBirth: LocalDate,
    val dateOfLatestCheckup: LocalDateTime?,
    val gender: Gender,
    val sportsmanCategory: SportsmanCategory,
    val email: String
) {
    companion object {
        fun fromEntity(patient: Patient): PatientDTO {
            return PatientDTO(
                patientId = patient.patientId!!,
                name = "${patient.firstName} ${patient.lastName}",
                dateOfBirth = patient.dateOfBirth,
                dateOfLatestCheckup = patient.dateOfLatestCheckUp,
                gender = patient.gender,
                sportsmanCategory = patient.sportsmanCategory,
                email = patient.email
            )
        }
    }
}