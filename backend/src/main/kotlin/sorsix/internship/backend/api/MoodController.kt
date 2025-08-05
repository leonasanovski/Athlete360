package sorsix.internship.backend.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.internship.backend.dto.MoodDTO
import sorsix.internship.backend.mappers.MoodMapper
import sorsix.internship.backend.model.Mood
import sorsix.internship.backend.repository.MoodRepository
import sorsix.internship.backend.service.MoodService

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/mood")
class MoodController(
    private val moodRepository: MoodRepository
) {
    @GetMapping
    fun getMoods(): ResponseEntity<List<MoodDTO>> {
        val moods = moodRepository.findAll().map { MoodMapper.mapMoodToResponseDto(it) }
        return ResponseEntity.ok(moods)
    }
}