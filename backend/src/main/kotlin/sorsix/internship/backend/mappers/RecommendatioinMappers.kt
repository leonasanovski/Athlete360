package sorsix.internship.backend.mappers

import sorsix.internship.backend.dto.RecommendationResponse
import sorsix.internship.backend.model.Recommendation

fun Recommendation.toDto(): RecommendationResponse = RecommendationResponse(
    recommendationId = recommendationId,
    reportId = report.reportId!!,
    type = type,
    restrictionLevel = restrictionLevel,
    label = label,
    description = description,
    costPerMonth = costPerMonth,
    durationWeeks = durationWeeks,
    frequencyPerDay = frequencyPerDay,
    targetGoal = targetGoal,
    effectivenessRating = effectivenessRating,
    doctorPersonalizedNotes = doctorPersonalizedNotes
)