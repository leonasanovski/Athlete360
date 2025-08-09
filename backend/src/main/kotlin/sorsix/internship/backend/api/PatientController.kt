package sorsix.internship.backend.api

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.internship.backend.dto.AthleteReportShortDTO
import sorsix.internship.backend.dto.RecommendationResponse
import sorsix.internship.backend.service.AthleteReportService
import sorsix.internship.backend.service.RecommendationService

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/patient")
class PatientController(val athleteReportService: AthleteReportService, val recommendationService: RecommendationService) {

    @GetMapping("{patientId}/reports")
    fun getPatientReports(@PathVariable patientId: Long,
                         @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ) : ResponseEntity<Page<AthleteReportShortDTO>> =
        ResponseEntity.ok(athleteReportService.getReportsShortByPatientId(patientId, pageable));

    @GetMapping("{patientId}/latest/recommendations")
    fun getPatientRecommendationsForLatestReport(@PathVariable patientId: Long): ResponseEntity<List<RecommendationResponse>> {
        val latestReportId = athleteReportService.findLatestReportIdByPatientId(patientId)
        val recommendations = recommendationService.findRecommendationsByReportId(latestReportId)
        return ResponseEntity.ok(recommendations)
    }
}