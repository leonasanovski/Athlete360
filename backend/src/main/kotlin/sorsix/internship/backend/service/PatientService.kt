package sorsix.internship.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import sorsix.internship.backend.dto.PatientDTO
import sorsix.internship.backend.dto.PatientDataDTO
import sorsix.internship.backend.model.Patient

interface PatientService {
    fun getPatientsByDoctorId(doctorId: Long, pageable: Pageable): Page<PatientDTO>
    fun searchPatientsByDoctorIdAndEmbg(doctorId: Long, embg: String, pageable: Pageable): Page<PatientDTO>
    fun createPatientFromUser(patientData: PatientDataDTO, userId: Long): Patient;
}