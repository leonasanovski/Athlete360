package sorsix.internship.backend.dto
import sorsix.internship.backend.model.enum.MoodProgress
import java.time.LocalDateTime

data class ProgressEntry(
    val date: LocalDateTime,
    val progress: MoodProgress
)
