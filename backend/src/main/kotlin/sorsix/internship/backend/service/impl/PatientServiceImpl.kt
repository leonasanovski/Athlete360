package sorsix.internship.backend.service.impl

import org.springframework.data.crossstore.ChangeSetPersister
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

    override fun createPatientFromUser(patientData: PatientDataDTO, userId: Long): Patient {
        val user =
            userRepository.findById(userId).orElseThrow { throw RuntimeException("User with id = $userId not found") }
        println("In CREATE PATIENT FROM USER")
        val patient = Patient(
            user = user,
            doctor = null,
            dateOfBirth = LocalDate.parse(patientData.dateOfBirth),
            dateOfLatestCheckUp = null,
            gender = patientData.gender,
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

}