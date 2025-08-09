package sorsix.internship.backend.dto
import sorsix.internship.backend.model.enum.Gender
import sorsix.internship.backend.model.enum.SportsmanCategory
import java.time.LocalDate

data class PatientDTO(
    val patientId: Long,
    val firstName: String,
    val lastName: String,
    val doctorInfo: String,
    val gender: Gender,
    val sportCategory: SportsmanCategory,
    val email: String,
    val dateOfBirth: LocalDate
)
