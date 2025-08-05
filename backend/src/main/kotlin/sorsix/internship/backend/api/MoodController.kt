package sorsix.internship.backend.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.internship.backend.dto.MoodDTO
import sorsix.internship.backend.mappers.MoodMapper
import sorsix.internship.backend.repository.MoodRepository

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/moods")
class MoodController(
    private val moodRepository: MoodRepository
) {
    @GetMapping
    fun getAllMoods(): ResponseEntity<List<MoodDTO>> {
        val moods = moodRepository.findAll().map { MoodMapper.mapMoodToResponseDto(it) }
        return ResponseEntity.ok(moods)
    }

    @GetMapping("/{id}")
    fun getMoodsForPatient(@PathVariable id: Long): ResponseEntity<List<MoodDTO>> {
        val moods = moodRepository.findByPatientPatientId(id).map { MoodMapper.mapMoodToResponseDto(it) }
        return ResponseEntity.ok(moods)
    }

    @GetMapping("/info/{moodId}")
    fun getSpecificMoodForPatient(
        @PathVariable moodId: Long
    ): ResponseEntity<MoodDTO> {
        val mood = moodRepository.findByMoodId(moodId)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(MoodMapper.mapMoodToResponseDto(mood))
    }
}