package sorsix.internship.backend.service.impl

import org.springframework.stereotype.Service
import sorsix.internship.backend.repository.PatientRepository
import sorsix.internship.backend.service.PatientService

@Service
class PatientServiceImpl(private val patientRepository: PatientRepository) : PatientService {
}