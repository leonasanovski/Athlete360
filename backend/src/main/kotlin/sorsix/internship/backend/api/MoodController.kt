package sorsix.internship.backend.api

import org.springframework.data.domain.Page
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

@RestController
@RequestMapping("/api/moods")
@CrossOrigin(origins = ["http://localhost:4200"])
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
    ): Page<MoodDTO> = moodService
        .findAllFiltered(
            patientId,
            from?.let { LocalDateTime.parse(from) },
            to?.let { LocalDateTime.parse(to) },
            moodEmotion,
            moodProgress,
            pageSize,
            pageNumber
        )
        .map { MoodMapper.mapMoodToResponseDto(it) }


    @GetMapping("/{patientId}/all-moods")
    fun getMoodsForPatient(@PathVariable patientId: Long): List<MoodDTO> =
        moodRepository
            .findByPatientPatientId(patientId)
            .map { MoodMapper.mapMoodToResponseDto(it) }


    @GetMapping("/info/{moodId}")
    fun getSpecificMoodForPatient(@PathVariable moodId: Long): ResponseEntity<MoodDTO> =
        moodRepository.findByMoodId(moodId)
            ?.let { ResponseEntity.ok(MoodMapper.mapMoodToResponseDto(it)) }
            ?: ResponseEntity.notFound().build()

    @PostMapping
    fun addMood(@RequestBody dto: MoodCreatingResponseDTO): ResponseEntity<MoodDTO> =
        ResponseEntity.status(HttpStatus.CREATED)
            .body(moodService.save(dto).let { MoodMapper.mapMoodToResponseDto(it) })


    @GetMapping("/{id}/statistics")
    fun getPatientMoodStatistics(@PathVariable id: Long): ResponseEntity<MoodStatisticsDTO> =
        ResponseEntity.ok(moodService.getMoodStatsForPatient(id))
}