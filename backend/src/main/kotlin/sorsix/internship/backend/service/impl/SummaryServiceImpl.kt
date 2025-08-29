package sorsix.internship.backend.service.impl

import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import sorsix.internship.backend.dto.SummaryCreateRequest
import sorsix.internship.backend.dto.SummaryDTO
import sorsix.internship.backend.mappers.toDto
import sorsix.internship.backend.repository.AthleteReportRepository
import sorsix.internship.backend.repository.SummaryRepository
import sorsix.internship.backend.service.RecommendationService
import sorsix.internship.backend.service.SummaryService

@Service
class SummaryServiceImpl(
    private val summaryRepository: SummaryRepository,
    private val athleteReportRepository: AthleteReportRepository,
    private val openAiService: OpenAiService,
    private val recommendationService: RecommendationService
): SummaryService {

    override fun findSummaryByReportId(reportId: Long): SummaryDTO {
        val report = athleteReportRepository.findById(reportId)
            .orElseThrow { throw EntityNotFoundException("There is no doctor with id=$reportId in the database.") }

        return summaryRepository.findByAthleteReportReportId(reportId)
            ?.toDto()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Summary for reportId=$reportId not found")
    }

    override fun create(summary: SummaryCreateRequest): Long {
        val report = athleteReportRepository.findById(summary.reportId!!)
            .orElseThrow { EntityNotFoundException("Report with id = ${summary.reportId} not found") }
        val res = SummaryCreateRequest.toEntity(summary, report)
        summaryRepository.save(res)

        return report.reportId!!
    }

    override fun getSummaryAI(reportId: Long): String {
        val report = athleteReportRepository.findById(reportId)
            .orElseThrow { EntityNotFoundException("Report with id = $reportId not found") }

        val recommendations = recommendationService.findRecommendationsByReportId(reportId)
        val summarized = openAiService.summarizeRecommendations(recommendations)

        return summarized
    }

    override fun update(reportId: Long, request: SummaryCreateRequest): Long {
        val summary = summaryRepository.findByAthleteReportReportId(reportId)
            ?: throw EntityNotFoundException("Report with id = $reportId not found")

        request.summarizedContent?.let { summary.summarizedContent = it }

        summaryRepository.save(summary)
        return reportId
    }
}