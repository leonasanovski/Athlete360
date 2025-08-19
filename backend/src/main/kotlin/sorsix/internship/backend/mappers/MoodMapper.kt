package sorsix.internship.backend.mappers

import sorsix.internship.backend.dto.MoodDTO
import sorsix.internship.backend.model.Mood

object MoodMapper {
    fun mapMoodToResponseDto(mood: Mood): MoodDTO = MoodDTO(
        moodId = mood.moodId,
        moodProgress = mood.moodProgress.name,
        moodEmotion = mood.moodEmotion.name,
        hoursSleptAverage = mood.hoursSleptAverage,
        moodDescription = mood.moodDescription,
        moodDescriptionScore = mood.moodDescriptionScore,
        createdAt = mood.createdAt.toString(),
        patientId = mood.patient.patientId ?: 0,
        patientName = "${mood.patient.user.firstName} ${mood.patient.user.lastName}"
    )
}