package sorsix.internship.backend.components

import org.springframework.stereotype.Component
import sorsix.internship.backend.dto.MetricFlagDTO
import sorsix.internship.backend.model.enum.FlagLevel
import java.math.BigDecimal
import kotlin.math.pow

@Component
class MetricsFlagHelper {
    fun flagBMI(weight: Double, height: Double): MetricFlagDTO<Double> {
        val bmi = weight / height.pow(2.0)
        val flag = when {
            bmi < 18.5 -> FlagLevel.RED
            bmi in 18.5..24.9 -> FlagLevel.GREEN
            bmi in 25.0..29.9 -> FlagLevel.YELLOW
            else -> FlagLevel.RED
        }
        return MetricFlagDTO(bmi, flag)
    }

    fun flagRestingHeartRate(hr: Int): MetricFlagDTO<Int> {
        val flag = when {
            hr < 50 -> FlagLevel.RED
            hr in 50..59 -> FlagLevel.YELLOW
            hr in 60..90 -> FlagLevel.GREEN
            hr in 91..100 -> FlagLevel.YELLOW
            else -> FlagLevel.RED
        }
        return MetricFlagDTO(hr, flag)
    }

    fun flagUnderPressureHeartRate(hr: Int, age: Int): MetricFlagDTO<Int> {
        val maxHR = 220 - age
        val percent = (hr.toDouble() / maxHR) * 100
        val flag = when {
            percent < 50 -> FlagLevel.RED
            percent in 50.0..85.0 -> FlagLevel.GREEN
            percent in 85.0..90.0 -> FlagLevel.YELLOW
            else -> FlagLevel.RED
        }
        return MetricFlagDTO(hr, flag)
    }

    fun flagLeanMass(weight: BigDecimal, leanMass: BigDecimal): MetricFlagDTO<BigDecimal> {
        val percent = (leanMass.toDouble() / weight.toDouble()) * 100
        val flag = when {
            percent < 70 -> FlagLevel.RED
            percent in 70.0..80.0 -> FlagLevel.YELLOW
            else -> FlagLevel.GREEN
        }
        return MetricFlagDTO(leanMass, flag)
    }

    fun flagBodyFat(bodyFat: BigDecimal): MetricFlagDTO<BigDecimal> {
        val bf = bodyFat.toDouble()
        val flag = when {
            bf < 6 -> FlagLevel.RED
            bf in 6.0..13.0 -> FlagLevel.GREEN
            bf in 13.1..17.0 -> FlagLevel.YELLOW
            else -> FlagLevel.RED
        }
        return MetricFlagDTO(bodyFat, flag)
    }

    fun flagBoneDensity(density: BigDecimal): MetricFlagDTO<BigDecimal> {
        val d = density.toDouble()
        val flag = when {
            d < 1.0 -> FlagLevel.RED
            d in 1.0..1.20 -> FlagLevel.YELLOW
            d in 1.21..1.35 -> FlagLevel.GREEN
            else -> FlagLevel.YELLOW
        }
        return MetricFlagDTO(density, flag)
    }

    fun flagHemoglobin(hemoglobin: BigDecimal, isMale: Boolean): MetricFlagDTO<BigDecimal> {
        val value = hemoglobin.toDouble()
        val flag = if (isMale) {
            when {
                value < 13.5 -> FlagLevel.RED
                value in 13.5..17.5 -> FlagLevel.GREEN
                value <= 18.5 -> FlagLevel.YELLOW
                else -> FlagLevel.RED
            }
        } else {
            when {
                value < 12.0 -> FlagLevel.RED
                value in 12.0..15.5 -> FlagLevel.GREEN
                value <= 17.0 -> FlagLevel.YELLOW
                else -> FlagLevel.RED
            }
        }
        return MetricFlagDTO(hemoglobin, flag)
    }

    fun flagGlucose(glucose: BigDecimal): MetricFlagDTO<BigDecimal> {
        val g = glucose.toDouble()
        val flag = when {
            g < 70 -> FlagLevel.RED
            g in 70.0..99.0 -> FlagLevel.GREEN
            g in 100.0..125.0 -> FlagLevel.YELLOW
            else -> FlagLevel.RED
        }
        return MetricFlagDTO(glucose, flag)
    }

    fun flagVitaminD(vitaminD: BigDecimal): MetricFlagDTO<BigDecimal> {
        val d = vitaminD.toDouble()
        val flag = when {
            d < 20 -> FlagLevel.RED
            d in 20.0..29.9 -> FlagLevel.YELLOW
            d in 30.0..50.0 -> FlagLevel.GREEN
            else -> FlagLevel.YELLOW
        }
        return MetricFlagDTO(vitaminD, flag)
    }

    fun flagIron(iron: BigDecimal): MetricFlagDTO<BigDecimal> {
        val i = iron.toDouble()
        val flag = when {
            i < 50 -> FlagLevel.RED
            i in 50.0..75.0 -> FlagLevel.YELLOW
            i in 76.0..170.0 -> FlagLevel.GREEN
            else -> FlagLevel.YELLOW
        }
        return MetricFlagDTO(iron, flag)
    }

    fun flagTestosterone(t: BigDecimal): MetricFlagDTO<BigDecimal> {
        val value = t.toDouble()
        val flag = when {
            value < 300 -> FlagLevel.RED
            value in 300.0..400.0 -> FlagLevel.YELLOW
            value in 400.0..1000.0 -> FlagLevel.GREEN
            else -> FlagLevel.RED
        }
        return MetricFlagDTO(t, flag)
    }

    fun flagCortisol(cortisol: BigDecimal): MetricFlagDTO<BigDecimal> {
        val c = cortisol.toDouble()
        val flag = when {
            c < 6 -> FlagLevel.RED
            c in 6.0..9.9 -> FlagLevel.YELLOW
            c in 10.0..20.0 -> FlagLevel.GREEN
            c in 20.1..25.0 -> FlagLevel.YELLOW
            else -> FlagLevel.RED
        }
        return MetricFlagDTO(cortisol, flag)
    }

    fun flagVo2Max(vo2Max: Double, age: Int, isMale: Boolean): MetricFlagDTO<BigDecimal> {
        val category = when {
            isMale -> when (age) {
                in 13..19 -> when {
                    vo2Max >= 60 -> FlagLevel.GREEN
                    vo2Max >= 50 -> FlagLevel.YELLOW
                    else -> FlagLevel.RED
                }
                in 20..29 -> when {
                    vo2Max >= 52 -> FlagLevel.GREEN
                    vo2Max >= 42 -> FlagLevel.YELLOW
                    else -> FlagLevel.RED
                }
                in 30..39 -> when {
                    vo2Max >= 47 -> FlagLevel.GREEN
                    vo2Max >= 38 -> FlagLevel.YELLOW
                    else -> FlagLevel.RED
                }
                in 40..49 -> when {
                    vo2Max >= 43 -> FlagLevel.GREEN
                    vo2Max >= 35 -> FlagLevel.YELLOW
                    else -> FlagLevel.RED
                }
                else -> when {
                    vo2Max >= 38 -> FlagLevel.GREEN
                    vo2Max >= 30 -> FlagLevel.YELLOW
                    else -> FlagLevel.RED
                }
            }
            else -> when (age) {
                in 13..19 -> when {
                    vo2Max >= 50 -> FlagLevel.GREEN
                    vo2Max >= 40 -> FlagLevel.YELLOW
                    else -> FlagLevel.RED
                }
                in 20..29 -> when {
                    vo2Max >= 45 -> FlagLevel.GREEN
                    vo2Max >= 35 -> FlagLevel.YELLOW
                    else -> FlagLevel.RED
                }
                in 30..39 -> when {
                    vo2Max >= 40 -> FlagLevel.GREEN
                    vo2Max >= 32 -> FlagLevel.YELLOW
                    else -> FlagLevel.RED
                }
                in 40..49 -> when {
                    vo2Max >= 36 -> FlagLevel.GREEN
                    vo2Max >= 28 -> FlagLevel.YELLOW
                    else -> FlagLevel.RED
                }
                else -> when {
                    vo2Max >= 33 -> FlagLevel.GREEN
                    vo2Max >= 25 -> FlagLevel.YELLOW
                    else -> FlagLevel.RED
                }
            }
        }
        return MetricFlagDTO(vo2Max.toBigDecimal(), category)
    }
}