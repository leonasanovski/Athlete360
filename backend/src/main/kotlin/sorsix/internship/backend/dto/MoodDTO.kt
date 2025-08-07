package sorsix.internship.backend.dto

data class MoodDTO(
    val moodId: Long?,
    val moodProgress: String,
    val moodEmotion: String,
    val hoursSleptAverage: Int,
    val moodDescription: String,
    val moodDescriptionScore: Int,
    val createdAt: String,
    val patientId: Long,
    val patientName: String
)
