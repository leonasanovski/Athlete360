package sorsix.internship.backend.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.internship.backend.dto.*
import sorsix.internship.backend.repository.AthleteReportRepository
import sorsix.internship.backend.service.AthleteReportService
import sorsix.internship.backend.service.RecommendationService
import sorsix.internship.backend.service.SummaryService


@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = ["http://localhost:4200"])
class AthleteReportController(
    private val athleteReportService: AthleteReportService,
    private val recommendationService: RecommendationService,
    private val summaryService: SummaryService,
    private val athleteReportRepository: AthleteReportRepository
) {

    @PostMapping
    fun createReport(@RequestBody request: AthleteReportFormDTO): ResponseEntity<Long> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(athleteReportService.create(request))

    @GetMapping("/{id}")
    fun getReport(@PathVariable id: Long): ResponseEntity<AthleteReportResponse> =
        ResponseEntity.ok(athleteReportService.findReportById(id))

    @GetMapping("/{reportId}/flags")
    fun getFlagsForStats(@PathVariable reportId: Long): ResponseEntity<ReportMetricFlaggerDTO> =
        athleteReportRepository.findById(reportId).let {
            val flagger = athleteReportService.reportMetricsFlagging(reportId)
            return ResponseEntity.ok(flagger)
        }

    @GetMapping("/{id}/recommendations")
    fun getRecommendationsByReport(@PathVariable id: Long): ResponseEntity<List<RecommendationResponse>> =
        ResponseEntity.ok(recommendationService.findRecommendationsByReportId(id))

    @GetMapping("/{id}/summary")
    fun getSummaryByReport(@PathVariable id: Long): ResponseEntity<SummaryDTO> =
        ResponseEntity.ok(summaryService.findSummaryByReportId(id))
}