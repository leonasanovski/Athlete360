package sorsix.internship.backend.service

import sorsix.internship.backend.model.AthleteReport
import sorsix.internship.backend.model.Doctor
import sorsix.internship.backend.security.model.AppUser
import javax.print.Doc

interface DoctorService {
    fun createDoctorFromUser(userId: Long, specialization: String): Doctor
}