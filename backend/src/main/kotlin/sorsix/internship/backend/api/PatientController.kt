package sorsix.internship.backend.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.internship.backend.dto.AthleteReportShortDTO
import sorsix.internship.backend.service.AthleteReportService

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/patient")
class PatientController(val athleteReportService: AthleteReportService) {

    @GetMapping("{patientId}/reports")
    fun getPatientReports(@PathVariable patientId: Long) : ResponseEntity<List<AthleteReportShortDTO>> =
        ResponseEntity.ok(athleteReportService.getReportsShortByPatientId(patientId));
}