package sorsix.internship.backend.api

import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.internship.backend.dto.AthleteReportShortDTO
import sorsix.internship.backend.dto.DoctorCreateRequest
import sorsix.internship.backend.dto.PatientDTO
import sorsix.internship.backend.model.Doctor
import sorsix.internship.backend.model.Patient
import sorsix.internship.backend.repository.DoctorRepository
import sorsix.internship.backend.service.AthleteReportService
import sorsix.internship.backend.service.DoctorService
import sorsix.internship.backend.service.PatientService

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/doctor")
class DoctorController(
    val doctorRepository: DoctorRepository,
    val doctorService: DoctorService,
    val athleteReportService: AthleteReportService,
    val patientService: PatientService
) {

    @GetMapping
    fun getAllDoctors(): List<Doctor> = doctorRepository.findAll()

    @PostMapping
    fun createDoctor(@Valid @RequestBody body: DoctorCreateRequest): ResponseEntity<Doctor> {
        val saved = doctorRepository.save(
            Doctor(
                firstName = body.firstName!!,
                lastName = body.lastName!!,
                specialization = body.specialization!!,
                email = body.email!!
            )
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(saved)
    }

    @GetMapping("/{id}")
    fun getDoctor(@PathVariable id: Long): ResponseEntity<Doctor> {
        val d = doctorRepository
            .findById(id)
            .orElseThrow { NoSuchElementException("Doctor not found") }
        return ResponseEntity.ok(d)
    }

    @GetMapping("{doctorId}/patients")
    fun getDoctorPatients(
        @PathVariable doctorId: Long,
        @PageableDefault(size = 10, sort = ["dateOfLatestCheckUp"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<PatientDTO>> =
        ResponseEntity.ok(patientService.getPatientsByDoctorId(doctorId, pageable))

    @GetMapping("{doctorId}/patients/search")
    fun searchDoctorPatients(
        @PathVariable doctorId: Long,
        @RequestParam(required = false, defaultValue = "") embg: String,
        @PageableDefault(size = 10, sort = ["dateOfLatestCheckUp"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<PatientDTO>> {
        return ResponseEntity.ok(patientService.searchPatientsByDoctorIdAndEmbg(doctorId, embg, pageable))
    }



    @GetMapping("{doctorId}/reports")
    fun getDoctorReports(@PathVariable doctorId: Long,
                         @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ) : ResponseEntity<Page<AthleteReportShortDTO>> =
        ResponseEntity.ok(athleteReportService.getReportsShortByDoctorId(doctorId, pageable));
}