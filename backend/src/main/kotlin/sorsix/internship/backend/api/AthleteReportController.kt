package sorsix.internship.backend.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.internship.backend.dto.AthleteReportCreateRequest
import sorsix.internship.backend.dto.AthleteReportResponse
import sorsix.internship.backend.model.AthleteReport
import sorsix.internship.backend.repository.AthleteReportRepository
import sorsix.internship.backend.service.AthleteReportService

@RestController
@RequestMapping("/api/reports")
class AthleteReportController(
    private val athleteReportService: AthleteReportService,
    private val athleteReportRepository: AthleteReportRepository
) {

    @PostMapping
    fun createReport(@RequestBody request: AthleteReportCreateRequest): ResponseEntity<Void> {
        val reportId = athleteReportService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED)
            .header("Location", "/api/reports/$reportId")
            .build()
    }

    @GetMapping("/{id}")
    fun getReport(@PathVariable id: Long): ResponseEntity<AthleteReportResponse> =
        ResponseEntity.ok(athleteReportService.findReportById(id))

}