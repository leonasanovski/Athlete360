package sorsix.internship.backend.service.impl

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import sorsix.internship.backend.model.AthleteReport
import sorsix.internship.backend.model.Doctor
import sorsix.internship.backend.repository.AthleteReportRepository
import sorsix.internship.backend.repository.DoctorRepository
import sorsix.internship.backend.security.model.UserRole
import sorsix.internship.backend.security.repository.AppUserRepository
import sorsix.internship.backend.service.DoctorService

@Service
class DoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val userRepository: AppUserRepository
) : DoctorService {
}