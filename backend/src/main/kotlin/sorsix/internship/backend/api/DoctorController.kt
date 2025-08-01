package sorsix.internship.backend.api

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.internship.backend.dto.DoctorCreateRequest
import sorsix.internship.backend.model.Doctor
import sorsix.internship.backend.repository.DoctorRepository

@RestController
@RequestMapping("/api/doctor")
class DoctorController(val doctorRepository: DoctorRepository) {

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
}