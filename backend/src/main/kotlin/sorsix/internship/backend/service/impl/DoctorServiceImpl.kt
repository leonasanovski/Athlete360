package sorsix.internship.backend.service.impl

import org.springframework.stereotype.Service
import sorsix.internship.backend.repository.DoctorRepository
import sorsix.internship.backend.service.DoctorService

@Service
class DoctorServiceImpl(private val doctorRepository: DoctorRepository) : DoctorService {
}