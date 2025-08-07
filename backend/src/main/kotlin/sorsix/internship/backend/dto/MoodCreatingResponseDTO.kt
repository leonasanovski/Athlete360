package sorsix.internship.backend.dto

data class MoodCreatingResponseDTO(
    val patientId: Long,
    val moodEmotion: String,
    val moodDescription: String,
    val hoursSleptAverage: Int
)
