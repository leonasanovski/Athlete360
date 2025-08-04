package sorsix.internship.backend.dto

import java.math.BigDecimal

data class ReportMetricFlaggerDTO(
    var vo2Max: MetricFlagDTO<BigDecimal>? = null,
    var restingHeartRate: MetricFlagDTO<Int>? = null,
    var underPressureHeartRate: MetricFlagDTO<Int>? = null,
    var bodyFatPercentage: MetricFlagDTO<BigDecimal>? = null,
    var leanMuscleMass: MetricFlagDTO<BigDecimal>? = null,
    var boneDensity: MetricFlagDTO<BigDecimal>? = null,
    var bmi: MetricFlagDTO<Double>? = null,
    var hemoglobin: MetricFlagDTO<BigDecimal>? = null,
    var glucose: MetricFlagDTO<BigDecimal>? = null,
    var vitaminD: MetricFlagDTO<BigDecimal>? = null,
    var iron: MetricFlagDTO<BigDecimal>? = null,
    var testosterone: MetricFlagDTO<BigDecimal>? = null,
    var cortisol: MetricFlagDTO<BigDecimal>? = null
)