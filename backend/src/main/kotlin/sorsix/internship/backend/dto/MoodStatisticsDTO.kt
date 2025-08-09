package sorsix.internship.backend.dto

import sorsix.internship.backend.model.enum.MoodEmotion
import sorsix.internship.backend.model.enum.MoodProgress

data class MoodStatisticsDTO(
    val averageSleepOverall: Double,
    val mostFrequentEmotion: MoodEmotion,
    val mostFrequentProgressState: MoodProgress,
    val totalMoodEntries: Int,
    val moodEmotionCounts: Map<MoodEmotion, Int>,
    val moodProgressCounts: Map<MoodProgress, Int>,
    val progressOverTime: List<ProgressEntry>
)
