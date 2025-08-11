package sorsix.internship.backend.mappers

import sorsix.internship.backend.dto.PatientDTO
import sorsix.internship.backend.model.Patient

object PatientMapper {
    fun mapPatientToResponseDTO(patient: Patient): PatientDTO = PatientDTO(
        patientId = patient.patientId!!,
        name = "${patient.firstName} ${patient.lastName}",
        dateOfBirth = patient.dateOfBirth,
        gender = patient.gender,
        dateOfLatestCheckup = patient.dateOfLatestCheckUp,
        embg = patient.embg,
        sportsmanCategory = patient.sportsmanCategory,
        doctor = "Dr. ${patient.doctor.firstName} ${patient.doctor.lastName} with specialization in '${patient.doctor.specialization}'",
        email = patient.email
    )
}