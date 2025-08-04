package sorsix.internship.backend.service.impl

import org.springframework.stereotype.Service
import sorsix.internship.backend.model.AthleteReport
import sorsix.internship.backend.model.Doctor
import sorsix.internship.backend.repository.AthleteReportRepository
import sorsix.internship.backend.repository.DoctorRepository
import sorsix.internship.backend.service.DoctorService

@Service
class DoctorServiceImpl(
    private val doctorRepository: DoctorRepository,
    private val athleteReportRepository: AthleteReportRepository
) : DoctorService {

    override fun getReportsForDoctor(doctorId: Long): List<AthleteReport>? {
        if (!doctorRepository.existsById(doctorId)) throw NoSuchElementException("The doctor with id=$doctorId was not found")
        val reports = athleteReportRepository.findByDoctorDoctorId(doctorId)
        if (reports.isEmpty()) throw NoSuchElementException("There are no reports found for doctor with id=$doctorId")
        return reports
    }
}