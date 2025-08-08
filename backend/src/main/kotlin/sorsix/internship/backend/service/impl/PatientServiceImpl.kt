package sorsix.internship.backend.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import sorsix.internship.backend.dto.PatientDTO
import sorsix.internship.backend.repository.PatientRepository
import sorsix.internship.backend.service.PatientService

@Service
class PatientServiceImpl(private val patientRepository: PatientRepository) : PatientService {
    override fun getPatientsByDoctorId(
        doctorId: Long,
        pageable: Pageable
    ): Page<PatientDTO> {
        val sort = pageable.sort

        val adjustedSort = if (sort.getOrderFor("name") != null) {
            val direction = sort.getOrderFor("name")!!.direction
            Sort.by(direction, "firstName").and(Sort.by(direction, "lastName"))
        } else {
            sort
        }

        val adjustedPageable = PageRequest.of(
            pageable.pageNumber,
            pageable.pageSize,
            adjustedSort
        )

        return patientRepository.findByDoctorDoctorId(doctorId, adjustedPageable)
            .map { PatientDTO.fromEntity(it) }
    }
}