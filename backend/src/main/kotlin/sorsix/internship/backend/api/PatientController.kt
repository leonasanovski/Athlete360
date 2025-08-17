package sorsix.internship.backend.api

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import sorsix.internship.backend.dto.AthleteReportShortDTO
import sorsix.internship.backend.dto.PatientDTO
import sorsix.internship.backend.dto.RecommendationResponse
import sorsix.internship.backend.mappers.PatientMapper
import sorsix.internship.backend.repository.PatientRepository
import sorsix.internship.backend.service.AthleteReportService
import sorsix.internship.backend.service.RecommendationService

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = ["http://localhost:4200"])
class PatientController(val athleteReportService: AthleteReportService, val recommendationService: RecommendationService, val patientRepository: PatientRepository) {

    @GetMapping("/{patientId}")
    fun getPatientObject(@PathVariable patientId: Long): ResponseEntity<PatientDTO> = ResponseEntity.ok(
        PatientMapper.mapPatientToResponseDTO(
            patientRepository.findById(patientId)
                .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND) }
        )
    )

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