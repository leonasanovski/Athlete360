package sorsix.internship.backend.service
import org.springframework.data.domain.Page
import sorsix.internship.backend.dto.MoodCreatingResponseDTO
import sorsix.internship.backend.dto.MoodStatisticsDTO
import sorsix.internship.backend.model.Mood
import sorsix.internship.backend.model.enum.MoodEmotion
import sorsix.internship.backend.model.enum.MoodProgress
import java.time.LocalDateTime

interface MoodService {
    fun save(dto: MoodCreatingResponseDTO): Mood
    fun getMoodStatsForPatient(patientId: Long): MoodStatisticsDTO
    fun findAllFiltered(
        patientId: Long?,
        from: LocalDateTime?,
        to: LocalDateTime?,
        moodEmotion: List<MoodEmotion>?,
        moodProgress: List<MoodProgress>?,
        pageSize: Int,
        pageNumber: Int
    ): Page<Mood>
}