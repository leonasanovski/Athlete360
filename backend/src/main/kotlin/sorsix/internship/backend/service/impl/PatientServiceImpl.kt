package sorsix.internship.backend.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import sorsix.internship.backend.dto.PatientDTO
import sorsix.internship.backend.dto.PatientDataDTO
import sorsix.internship.backend.model.Patient
import sorsix.internship.backend.model.enum.Gender
import sorsix.internship.backend.repository.PatientRepository
import sorsix.internship.backend.security.repository.AppUserRepository
import sorsix.internship.backend.service.PatientService
import java.time.LocalDate

@Service
class PatientServiceImpl(
    private val patientRepository: PatientRepository,
    val userRepository: AppUserRepository
) : PatientService {
    override fun getPatientsByDoctorId(
        doctorId: Long,
        pageable: Pageable
    ): Page<PatientDTO> {
        val adjustedSort = adjustSortForFullName(pageable.sort)

        val adjustedPageable = PageRequest.of(
            pageable.pageNumber,
            pageable.pageSize,
            adjustedSort
        )
        return patientRepository.findByDoctorDoctorId(doctorId, adjustedPageable)
            .map { PatientDTO.fromEntity(it) }
    }

    override fun searchPatientsByDoctorIdAndEmbg(doctorId: Long, embg: String, pageable: Pageable): Page<PatientDTO> {
        val adjustedSort = adjustSortForFullName(pageable.sort)
        val adjustedPageable = PageRequest.of(
            pageable.pageNumber,
            pageable.pageSize,
            adjustedSort
        )
        return patientRepository
            .findByDoctorDoctorIdAndUserEmbgContaining(doctorId, embg, adjustedPageable)
            .map { PatientDTO.fromEntity(it) }
    }

    override fun getUnassignedPatients(embg: String, pageable: Pageable): Page<PatientDTO> {
        val adjustedSort = adjustSortForFullName(pageable.sort)
        val adjustedPageable = PageRequest.of(
            pageable.pageNumber,
            pageable.pageSize,
            adjustedSort
        )
        return patientRepository.findUnassignedUsers(embg, adjustedPageable).map { PatientDTO.fromEntity(it) }
    }

    override fun createPatientFromUser(patientData: PatientDataDTO, userId: Long): Patient {
        val user =
            userRepository.findById(userId).orElseThrow { throw RuntimeException("User with id = $userId not found") }
        val gender = if (user.embg.slice(7..9) == "455") {
            Gender.FEMALE
        } else {
            Gender.MALE
        }
        val date = parseToDate(user.embg.slice(0..6))
        val patient = Patient(
            user = user,
            doctor = null,
            dateOfBirth = date,
            dateOfLatestCheckUp = null,
            gender = gender,
            sportsmanCategory = patientData.sportsmanCategory
        )
        return patientRepository.save(patient)
    }

    private fun adjustSortForFullName(sort: Sort): Sort =
        if (sort.getOrderFor("name") != null) {
            val direction = sort.getOrderFor("name")!!.direction
            Sort.by(direction, "user.firstName").and(Sort.by(direction, "user.lastName"))
        } else {
            sort
        }

    private fun parseToDate(slicedEmbg: String): LocalDate =
        LocalDate.of(
            if (slicedEmbg.slice(4..6).toInt() < 26)
                "2${slicedEmbg.slice(4..6)}".toInt()
            else
                "1${slicedEmbg.slice(4..6)}".toInt(),
            slicedEmbg.slice(2..3).toInt(),
            slicedEmbg.slice(0..1).toInt()
        )
}