package sorsix.internship.backend.mappers

import sorsix.internship.backend.dto.PatientDTO
import sorsix.internship.backend.model.Patient

object PatientMapper {
    fun mapPatientToResponseDTO(patient: Patient): PatientDTO = PatientDTO(
        patientId = patient.patientId!!,
        name = "${patient.user.firstName} ${patient.user.lastName}",
        dateOfBirth = patient.dateOfBirth,
        gender = patient.gender,
        dateOfLatestCheckup = patient.dateOfLatestCheckUp,
        embg = patient.user.embg,
        sportsmanCategory = patient.sportsmanCategory,
        doctor = "Dr. ${patient.doctor?.user?.firstName} ${patient.doctor?.user?.lastName} with specialization in '${patient.doctor?.specialization}'",
        email = patient.user.email ?: ""
    )
}