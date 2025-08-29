package sorsix.internship.backend.service.impl

import jakarta.persistence.EntityNotFoundException
import jakarta.persistence.criteria.Predicate
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import sorsix.internship.backend.dto.MoodCreatingResponseDTO
import sorsix.internship.backend.dto.MoodStatisticsDTO
import sorsix.internship.backend.dto.ProgressEntry
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
            .orElseThrow { EntityNotFoundException("Patient not found with id: ${dto.patientId}") }

        val aiScore: Int = openAiService.getMoodScoreFromDescription(dto.moodDescription)
        val emotionScore = calculateEmotionScore(dto.moodEmotion)
        val sleepScore = calculateSleepScore(dto.hoursSleptAverage)
        val totalScore = calculateTotalMoodScore(aiScore, emotionScore, sleepScore)
        val moodProgress = determineMoodProgress(totalScore)

        println("AiScore is -> ${aiScore}, emotionScore is -> $emotionScore")
        println("EmotionScore is -> $emotionScore")
        println("SleepScore is -> $sleepScore")
        println("TOTAL SCORE: $totalScore with mood progress: $moodProgress")
        val mood = Mood(
            patient = patient,
            moodDescription = dto.moodDescription,
            moodEmotion = dto.moodEmotion,
            hoursSleptAverage = dto.hoursSleptAverage,
            createdAt = LocalDateTime.now(),
            moodProgress = moodProgress,
            moodDescriptionScore = totalScore.toInt()
        )
        println(mood)
        return moodRepository.save(mood)
    }

    override fun getMoodStatsForPatient(patientId: Long): MoodStatisticsDTO {
        val moodsForPatient: List<Mood> = moodRepository.findByPatientPatientId(patientId)
        val emotionMapCount: Map<MoodEmotion, Int> =
            MoodEmotion.entries.associateWith { emotion -> moodsForPatient.count { it.moodEmotion == emotion } }
        val progressMapCount: Map<MoodProgress, Int> =
            MoodProgress.entries.associateWith { progressStatus -> moodsForPatient.count { it.moodProgress == progressStatus } }
        val mostFrequentEmotion = emotionMapCount.maxByOrNull { it.value }?.key
        val mostFrequentProgress = progressMapCount.maxByOrNull { it.value }?.key
        val averageSleepOverall = moodsForPatient.map { it.hoursSleptAverage }.average()
        val progress: List<ProgressEntry> =
            moodsForPatient.sortedBy { it.createdAt }.map { ProgressEntry(it.createdAt, it.moodProgress) }
        return MoodStatisticsDTO(
            totalMoodEntries = moodsForPatient.size,
            moodEmotionCounts = emotionMapCount,
            moodProgressCounts = progressMapCount,
            averageSleepOverall = averageSleepOverall,
            mostFrequentProgressState = mostFrequentProgress ?: MoodProgress.STALL,
            mostFrequentEmotion = mostFrequentEmotion ?: MoodEmotion.NEUTRAL,
            progressOverTime = progress
        )
    }

    override fun findAllFiltered(
        patientId: Long?,
        from: LocalDateTime?,
        to: LocalDateTime?,
        moodEmotion: List<MoodEmotion>?,
        moodProgress: List<MoodProgress>?,
        pageSize: Int,
        pageNumber: Int
    ): Page<Mood> {
        //switch from and to if from is after too
        val (start, end) = when {
            from != null && to != null && from.isAfter(to) -> to to from
            else -> from to to
        }
        //building the specification
        val spec: Specification<Mood> = Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            val createdAt = root.get<LocalDateTime>("createdAt")
            when {
                start != null && end != null -> predicates += criteriaBuilder.between(createdAt, start, end)
                start != null -> predicates += criteriaBuilder.greaterThanOrEqualTo(createdAt, start)
                end != null -> predicates += criteriaBuilder.lessThanOrEqualTo(createdAt, end)
            }
            if (!moodEmotion.isNullOrEmpty()) {
                predicates.add(root.get<MoodEmotion>("moodEmotion").`in`(moodEmotion))
            }
            if (!moodProgress.isNullOrEmpty()) {
                predicates.add(root.get<MoodProgress>("moodProgress").`in`(moodProgress))
            }
            if (predicates.isEmpty()) {
                criteriaBuilder.conjunction()
            }
            patientId?.let {
                predicates += criteriaBuilder
                    .equal(
                        root.get<Any>("patient")
                            .get<Long>("patientId"), it
                    )
            }
            if (predicates.isEmpty()) criteriaBuilder.conjunction()
            else criteriaBuilder.and(*predicates.toTypedArray())
        }
        return moodRepository.findAll(
            spec,
            PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"))
        )
    }

    //helper functions
    private fun calculateEmotionScore(emotion: MoodEmotion): Int = when (emotion) {
        MoodEmotion.EXCITED -> 8
        MoodEmotion.HAPPY -> 10
        MoodEmotion.NEUTRAL -> 5
        MoodEmotion.TIRED -> 4
        MoodEmotion.STRESSED -> 3
        MoodEmotion.SAD -> 2
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