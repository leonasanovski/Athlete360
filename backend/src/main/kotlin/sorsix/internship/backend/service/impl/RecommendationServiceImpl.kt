package sorsix.internship.backend.service.impl

import org.springframework.stereotype.Service
import sorsix.internship.backend.repository.RecommendationRepository
import sorsix.internship.backend.service.RecommendationService

@Service
class RecommendationServiceImpl(private val recommendationRepository: RecommendationRepository) : RecommendationService {
}