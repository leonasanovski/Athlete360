package sorsix.internship.backend.dto

import sorsix.internship.backend.model.enum.SportsmanCategory

data class PatientDataDTO(
    val id: Int,
    val sportsmanCategory: SportsmanCategory
)
