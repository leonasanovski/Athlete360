package sorsix.internship.backend.dto

import sorsix.internship.backend.model.enum.FlagLevel

data class MetricFlagDTO<T>(
    val metric: T,
    val flag: FlagLevel
)
