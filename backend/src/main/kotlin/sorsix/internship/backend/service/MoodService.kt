package sorsix.internship.backend.service

import sorsix.internship.backend.dto.MoodCreatingResponseDTO
import sorsix.internship.backend.dto.MoodStatisticsDTO
import sorsix.internship.backend.model.Mood

interface MoodService {
    fun save(dto: MoodCreatingResponseDTO): Mood
    fun getMoodStatsForPatient(patientId: Long): MoodStatisticsDTO
}