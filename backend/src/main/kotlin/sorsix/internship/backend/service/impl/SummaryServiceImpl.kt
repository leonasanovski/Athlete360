package sorsix.internship.backend.service.impl

import org.springframework.stereotype.Service
import sorsix.internship.backend.repository.SummaryRepository
import sorsix.internship.backend.service.SummaryService

@Service
class SummaryServiceImpl(private val summaryRepository: SummaryRepository): SummaryService {
}