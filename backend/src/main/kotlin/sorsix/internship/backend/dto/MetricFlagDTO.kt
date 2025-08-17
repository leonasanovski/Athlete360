package sorsix.internship.backend.dto

import sorsix.internship.backend.model.enum.FlagLevel

data class MetricFlagDTO<T>(
    val value: T,
    val level: FlagLevel
)
