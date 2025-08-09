package sorsix.internship.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import sorsix.internship.backend.dto.PatientDTO

interface PatientService {
    //TODO GET ALL MOODS
    fun getPatientsByDoctorId(doctorId: Long, pageable: Pageable): Page<PatientDTO>
    fun searchPatientsByDoctorIdAndEmbg(doctorId: Long, embg: String, pageable: Pageable): Page<PatientDTO>
}