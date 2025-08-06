package sorsix.internship.backend.service.impl

import org.springframework.stereotype.Service
import sorsix.internship.backend.dto.MoodCreatingResponseDTO
import sorsix.internship.backend.model.Mood
import sorsix.internship.backend.model.enum.MoodEmotion
import sorsix.internship.backend.model.enum.MoodProgress
import sorsix.internship.backend.repository.MoodRepository
import sorsix.internship.backend.repository.PatientRepository
import sorsix.internship.backend.service.MoodService
import java.time.LocalDateTime

@Service
class MoodServiceImpl(
    private val moodRepository: MoodRepository,
    private val patientRepository: PatientRepository,
    private val openAiService: OpenAiService
) : MoodService {

    companion object {
        private const val POOR_SLEEP_PENALTY = 0.6
        private const val DEFAULT_SLEEP_MULTIPLIER = 1.0
        private const val GOOD_SLEEP_BOOST = 1.3
        private const val MIN_SCORE = 1.0
        private const val MAX_SCORE = 10.0
        private const val BAD_THRESHOLD = 4
        private const val STALL_THRESHOLD = 7
    }
    override fun save(dto: MoodCreatingResponseDTO): Mood {
        val patient = patientRepository.findById(dto.patientId)
            .orElseThrow { NoSuchElementException("Patient not found with id: ${dto.patientId}") }
        println(patient)
        val aiScore: Int = openAiService.getMoodScoreFromDescription(dto.moodDescription).toInt() //or default for test
        val emotionScore = calculateEmotionScore(dto.moodEmotion)
        val sleepScore = calculateSleepScore(dto.hoursSleptAverage)
        val totalScore = calculateTotalMoodScore(aiScore, emotionScore, sleepScore)
        val moodProgress = determineMoodProgress(totalScore)
        //testing
        println("AiScore is -> ${aiScore}, emotionScore is -> $emotionScore")
        println("EmotionScore is -> $emotionScore")
        println("SleepScore is -> $sleepScore")
        println("TOTAL SCORE: $totalScore with mood progress: $moodProgress")
        val mood = Mood(
            patient = patient,
            moodDescription = dto.moodDescription,
            moodEmotion = MoodEmotion.valueOf(dto.moodEmotion.uppercase()),
            hoursSleptAverage = dto.hoursSleptAverage,
            createdAt = LocalDateTime.now(),
            moodProgress = moodProgress
        )
        println(mood)
        return moodRepository.save(mood)
    }

    //helper functions
    private fun calculateEmotionScore(emotion: String): Int = when (emotion.uppercase()) {
        "EXCITED" -> 8
        "HAPPY" -> 10
        "NEUTRAL" -> 5
        "TIRED" -> 4
        "STRESSED" -> 3
        "SAD" -> 2
        else -> 5
    }

    private fun calculateSleepScore(hoursSlept: Int): Int = when (hoursSlept.toInt()) {
        in 0..4 -> 1
        in 5..6 -> 5
        in 7..9 -> 10
        in 10..12 -> 5
        else -> 1
    }

    private fun calculateTotalMoodScore(aiScore: Int, emotionScore: Int, sleepScore: Int): Double {
        val baseMoodScore = (aiScore + emotionScore) / 2.0
        val sleepMultiplier = when {
            sleepScore <= 1 -> POOR_SLEEP_PENALTY
            sleepScore <= 5 -> DEFAULT_SLEEP_MULTIPLIER
            sleepScore >= 10 -> GOOD_SLEEP_BOOST
            else -> DEFAULT_SLEEP_MULTIPLIER
        }
        val adjustedScore = when {
            sleepScore == 1 && emotionScore <= 3 -> baseMoodScore * sleepMultiplier * 0.8
            sleepScore == 10 && emotionScore >= 8 -> baseMoodScore * sleepMultiplier
            else -> baseMoodScore * sleepMultiplier
        }
        return adjustedScore.coerceIn(MIN_SCORE, MAX_SCORE)
    }

    private fun determineMoodProgress(totalScore: Double): MoodProgress = when {
        totalScore <= BAD_THRESHOLD -> MoodProgress.BAD
        totalScore <= STALL_THRESHOLD -> MoodProgress.STALL
        else -> MoodProgress.GOOD
    }
}