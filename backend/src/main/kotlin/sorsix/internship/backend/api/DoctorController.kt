package sorsix.internship.backend.api

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.Authentication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.internship.backend.dto.AthleteReportShortDTO
import sorsix.internship.backend.dto.CreateDoctorDTO
import sorsix.internship.backend.dto.PatientDTO
import sorsix.internship.backend.model.Doctor
import sorsix.internship.backend.repository.DoctorRepository
import sorsix.internship.backend.security.model.UserPrincipal
import sorsix.internship.backend.service.AthleteReportService
import sorsix.internship.backend.service.DoctorService
import sorsix.internship.backend.service.PatientService

@RestController
@RequestMapping("/api/doctor")
@CrossOrigin(origins = ["http://localhost:4200"])
class DoctorController(
    val doctorRepository: DoctorRepository,
    val doctorService: DoctorService,
    val athleteReportService: AthleteReportService,
    val patientService: PatientService
) {

    @GetMapping
    fun getAllDoctors(): List<Doctor> = doctorRepository.findAll()

    @GetMapping("/{id}")
    fun getDoctor(@PathVariable id: Long): ResponseEntity<Doctor> {
        val d = doctorRepository
            .findById(id)
            .orElseThrow { EntityNotFoundException("Doctor not found") }
        return ResponseEntity.ok(d)
    }

    @PostMapping("/create-doctor-user")
    fun createDoctorEntityForExistingUserWithRoleDoctor(
        @RequestBody doctorData: CreateDoctorDTO,
        authentication: Authentication
    ): ResponseEntity<Doctor> {
        val principal = authentication.principal as UserPrincipal
        val userId = principal.appUser.userId
        val doctor = doctorService.createDoctorFromUser(userId!!, doctorData.specialization)
        return ResponseEntity.ok(doctor)
    }

    @GetMapping("{doctorId}/patients")
    fun getDoctorPatients(
        @PathVariable doctorId: Long,
        @PageableDefault(
            size = 10,
            sort = ["dateOfLatestCheckUp"],
            direction = Sort.Direction.DESC
        ) pageable: Pageable
    ): ResponseEntity<Page<PatientDTO>> =
        ResponseEntity.ok(patientService.getPatientsByDoctorId(doctorId, pageable))

    @GetMapping("{doctorId}/patients/search")
    fun searchDoctorPatients(
        @PathVariable doctorId: Long,
        @RequestParam(required = false, defaultValue = "") embg: String,
        @RequestParam(required = false, defaultValue = false.toString()) patientType: Boolean?,
        @PageableDefault(size = 10, sort = ["dateOfLatestCheckUp"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<PatientDTO>> {
        if (patientType == true) {
            return ResponseEntity.ok(patientService.getUnassignedPatients(embg, pageable))
        }
        return ResponseEntity.ok(patientService.searchPatientsByDoctorIdAndEmbg(doctorId, embg, pageable))

    }

    @GetMapping("{doctorId}/reports")
    fun getDoctorReports(
        @PathVariable doctorId: Long,
        @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<Page<AthleteReportShortDTO>> =
        ResponseEntity.ok(athleteReportService.getReportsShortByDoctorId(doctorId, pageable))
}