package sorsix.internship.backend.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import sorsix.internship.backend.dto.SummaryDTO
import sorsix.internship.backend.mappers.toDto
import sorsix.internship.backend.repository.AthleteReportRepository
import sorsix.internship.backend.repository.SummaryRepository
import sorsix.internship.backend.service.SummaryService

@Service
class SummaryServiceImpl(private val summaryRepository: SummaryRepository,
                         private val athleteReportRepository: AthleteReportRepository
): SummaryService {

    override fun findSummaryByReportId(reportId: Long): SummaryDTO {
        val report = athleteReportRepository.findById(reportId)
            .orElseThrow { throw NoSuchElementException("There is no doctor with id=$reportId in the database.") }

        return summaryRepository.findByAthleteReportReportId(reportId)
            ?.toDto()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Summary for reportId=$reportId not found")
    }
}