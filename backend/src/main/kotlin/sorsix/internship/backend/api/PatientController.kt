package sorsix.internship.backend.api

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import sorsix.internship.backend.dto.*
import sorsix.internship.backend.mappers.PatientMapper
import sorsix.internship.backend.model.Doctor
import sorsix.internship.backend.model.Patient
import sorsix.internship.backend.repository.PatientRepository
import sorsix.internship.backend.security.model.UserPrincipal
import sorsix.internship.backend.service.AthleteReportService
import sorsix.internship.backend.service.PatientService
import sorsix.internship.backend.service.RecommendationService

@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = ["http://localhost:4200"])
class PatientController(
    val athleteReportService: AthleteReportService,
    val recommendationService: RecommendationService,
    val patientRepository: PatientRepository,
    val patientService: PatientService
) {
    @GetMapping("/{patientId}")
    fun getPatientObject(@PathVariable patientId: Long): ResponseEntity<PatientDTO> = ResponseEntity.ok(
        PatientMapper.mapPatientToResponseDTO(
            patientRepository.findById(patientId)
                .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND) }
        )
    )

    @PostMapping("/create-patient-user")
    fun createPatientFromUser(
        @RequestBody patientData: PatientDataDTO,
        authentication: Authentication
    ): ResponseEntity<Patient> {
        println("In Patient Controller")
        val principal = authentication.principal as UserPrincipal
        val userId = principal.appUser.userId
        val patient = patientService.createPatientFromUser(patientData, userId!!)
        return ResponseEntity.ok(patient)
    }

    @GetMapping("{patientId}/reports")
    fun getPatientReports(
        @PathVariable patientId: Long,
        @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<AthleteReportShortDTO>> =
        ResponseEntity.ok(athleteReportService.getReportsShortByPatientId(patientId, pageable));

    @GetMapping("{patientId}/latest/recommendations")
    fun getPatientRecommendationsForLatestReport(@PathVariable patientId: Long): ResponseEntity<List<RecommendationResponse>> {
        val latestReportId = athleteReportService.findLatestReportIdByPatientId(patientId)
        val recommendations = recommendationService.findRecommendationsByReportId(latestReportId)
        return ResponseEntity.ok(recommendations)
    }
}