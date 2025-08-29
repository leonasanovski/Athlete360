package sorsix.internship.backend.service

import sorsix.internship.backend.model.Doctor

interface DoctorService {
    fun createDoctorFromUser(userId: Long, specialization: String): Doctor
}