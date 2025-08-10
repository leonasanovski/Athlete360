package sorsix.internship.backend.api

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.internship.backend.dto.MoodCreatingResponseDTO
import sorsix.internship.backend.dto.MoodDTO
import sorsix.internship.backend.dto.MoodStatisticsDTO
import sorsix.internship.backend.mappers.MoodMapper
import sorsix.internship.backend.model.enum.MoodEmotion
import sorsix.internship.backend.model.enum.MoodProgress
import sorsix.internship.backend.repository.MoodRepository
import sorsix.internship.backend.service.MoodService
import java.time.LocalDateTime

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

    @GetMapping("/{patientId}/search")
    fun filterMoods(
        @PathVariable patientId: Long,
        @RequestParam(required = false) from: String?,
        @RequestParam(required = false) to: String?,
        @RequestParam(required = false) moodEmotion: List<MoodEmotion>?,
        @RequestParam(required = false) moodProgress: List<MoodProgress>?,
        @RequestParam(defaultValue = "4") pageSize: Int,
        @RequestParam(defaultValue = "1") pageNumber: Int
    ): Page<MoodDTO> {
        val fromDateTime = from?.let { LocalDateTime.parse(from) }
        val toDateTime = to?.let { LocalDateTime.parse(to) }
        return moodService
            .findAllFiltered(patientId, fromDateTime, toDateTime, moodEmotion, moodProgress, pageSize, pageNumber)
            .map { MoodMapper.mapMoodToResponseDto(it) }
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