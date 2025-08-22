package sorsix.internship.backend.service.impl

import org.springframework.stereotype.Service
import sorsix.internship.backend.model.Doctor
import sorsix.internship.backend.repository.DoctorRepository
import sorsix.internship.backend.security.repository.AppUserRepository
import sorsix.internship.backend.service.DoctorService

@Service
class DoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val userRepository: AppUserRepository
) : DoctorService {
    override fun createDoctorFromUser(
        userId: Long,
        specialization: String
    ): Doctor {
        val user = userRepository.findById(userId)
            .orElseThrow { throw RuntimeException("User with id $userId does not exist") }
        val doctor = Doctor(user = user, specialization = specialization)
        return doctorRepository.save(doctor)
    }
}