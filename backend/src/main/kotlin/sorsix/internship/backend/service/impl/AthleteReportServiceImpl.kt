package sorsix.internship.backend.service.impl

import org.springframework.stereotype.Service
import sorsix.internship.backend.model.AthleteReport
import sorsix.internship.backend.repository.AthleteReportRepository
import sorsix.internship.backend.service.AthleteReportService

@Service
class AthleteReportServiceImpl(private val athleteReportRepository: AthleteReportRepository) : AthleteReportService {
}