package sorsix.internship.backend.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.internship.backend.dto.MoodCreatingResponseDTO
import sorsix.internship.backend.dto.MoodDTO
import sorsix.internship.backend.dto.MoodStatisticsDTO
import sorsix.internship.backend.mappers.MoodMapper
import sorsix.internship.backend.repository.MoodRepository
import sorsix.internship.backend.service.MoodService

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/moods")
class MoodController(
    private val moodRepository: MoodRepository,
    private val moodService: MoodService
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

    @PostMapping
    fun addMood(@RequestBody dto: MoodCreatingResponseDTO): ResponseEntity<MoodDTO> {
        val mood = moodService.save(dto)
        val moodDTO = MoodMapper.mapMoodToResponseDto(mood)
        return ResponseEntity.status(HttpStatus.CREATED).body(moodDTO)
    }

    @GetMapping("/{id}/statistics")
    fun getPatientMoodStatistics(@PathVariable id: Long): ResponseEntity<MoodStatisticsDTO> =
        ResponseEntity.ok(moodService.getMoodStatsForPatient(id))
}