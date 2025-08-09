package sorsix.internship.backend.api

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
import sorsix.internship.backend.mappers.PatientMapper
import sorsix.internship.backend.repository.PatientRepository
import sorsix.internship.backend.service.AthleteReportService

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/patient")
class PatientController(val athleteReportService: AthleteReportService, val patientRepository: PatientRepository) {
    @GetMapping("/{patientId}/reports")
    fun getPatientReports(@PathVariable patientId: Long): ResponseEntity<List<AthleteReportShortDTO>> =
        ResponseEntity.ok(athleteReportService.getReportsShortByPatientId(patientId))

    @GetMapping("/{patientId}")
    fun getPatientObject(@PathVariable patientId: Long): ResponseEntity<PatientDTO> = ResponseEntity.ok(
        PatientMapper.mapPatientToResponseDTO(
            patientRepository.findById(patientId)
                .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND) }
        )
    )
}