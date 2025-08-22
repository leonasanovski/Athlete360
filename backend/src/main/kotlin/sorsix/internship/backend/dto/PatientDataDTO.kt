package sorsix.internship.backend.dto

import sorsix.internship.backend.model.enum.Gender
import sorsix.internship.backend.model.enum.SportsmanCategory

data class PatientDataDTO(
    val dateOfBirth: String,
    val gender: Gender,
    val sportsmanCategory: SportsmanCategory
)
