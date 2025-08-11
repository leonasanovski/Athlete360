package sorsix.internship.backend.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sorsix.internship.backend.model.Doctor
import sorsix.internship.backend.model.Patient
import sorsix.internship.backend.model.enum.Gender
import sorsix.internship.backend.model.enum.SportsmanCategory
import sorsix.internship.backend.repository.AthleteReportRepository
import sorsix.internship.backend.repository.DoctorRepository
import sorsix.internship.backend.repository.PatientRepository
import java.time.LocalDate


@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api/admin")//todo make it only for admins
class AdminDataController(
    private val doctorRepository: DoctorRepository,
    private val patientRepository: PatientRepository,
    private val athleteReportRepository: AthleteReportRepository
) {
//    @PostMapping("/create-sample-doctors")
//    fun createSampleDoctors(): ResponseEntity<String> {
//        val doctors = listOf(
//            Doctor(
//                firstName = "Petar",
//                lastName = "Avramovikj",
//                specialization = "Sports Medicine Doctor",
//                email = "petar.avramovikj@sorsix.com"
//            ),
//            Doctor(
//                firstName = "Jaglika",
//                lastName = "Stojkovska",
//                specialization = "Physio Therapist Doctor",
//                email = "jaglika.stojkovska@remedika.com"
//            )
//        )
//        val saved = doctorRepository.saveAll(doctors)
//        return ResponseEntity.ok("There are total ${saved.size} doctors in the database")
//    }
//
//    @PostMapping("/create-sample-patients")
//    fun createSamplePatients(): ResponseEntity<String> {
//        val doc1 = doctorRepository
//            .findById(1L)
//            .orElseThrow { IllegalStateException("Doc not found") }
//        val doc2 = doctorRepository
//            .findById(2L)
//            .orElseThrow { IllegalStateException("Doc not found") }
//
//        val patients = listOf(
//            Patient(
//                doctor = doc1,
//                firstName = "Leon",
//                lastName = "Asanovski",
//                dateOfBirth = LocalDate.parse("2003-04-11"),
//                gender = Gender.MALE,
//                sportsmanCategory = SportsmanCategory.AMATEUR,
//                email = "leon.asanovski@sorsix.com"
//            ),
//            Patient(
//                doctor = doc2,
//                firstName = "Strahil",
//                lastName = "Pavloski",
//                dateOfBirth = LocalDate.parse("1987-09-29"),
//                gender = Gender.MALE,
//                sportsmanCategory = SportsmanCategory.AMATEUR,
//                email = "strahil.pavloski@gmail.com"
//            )
//        )
//        println(patients)
//        val saved = patientRepository.saveAll(patients)
//        return ResponseEntity.ok("There are total ${saved.size} patients in the database")
//    }
//
//    @GetMapping("/status")
//    fun getDataStatus(): ResponseEntity<Map<String, Any>> {
//        return ResponseEntity.ok(
//            mapOf(
//                "doctors_count" to doctorRepository.count(),
//                "patients_count" to patientRepository.count(),
//                "report_count" to athleteReportRepository.count(),
//                "message" to "Admin controller is working"
//            )
//        )
//    }
}