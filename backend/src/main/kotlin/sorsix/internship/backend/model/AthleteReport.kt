package sorsix.internship.backend.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "athlete_report")
data class AthleteReport(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    val reportId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    val doctor: Doctor,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    val patient: Patient,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "vo2_max", nullable = false)
    val vo2Max: BigDecimal,

    @Column(name = "resting_heart_rate", nullable = false)
    val restingHeartRate: Int,

    @Column(name = "under_pressure_heart_rate", nullable = false)
    val underPressureHeartRate: Int,

    @Column(name = "body_fat_percentage", nullable = false)
    val bodyFatPercentage: BigDecimal,

    @Column(name = "lean_muscle_mass", nullable = false)
    val leanMuscleMass: BigDecimal,

    @Column(name = "bone_density", nullable = false)
    val boneDensity: BigDecimal,

    @Column(name = "height", nullable = false)
    val height: BigDecimal,

    @Column(name = "weight", nullable = false)
    val weight: BigDecimal,

    @Column(name = "one_rep_max_bench", nullable = false)
    val oneRepMaxBench: BigDecimal,

    @Column(name = "one_rep_max_squat", nullable = false)
    val oneRepMaxSquat: BigDecimal,

    @Column(name = "one_rep_max_deadlift", nullable = false)
    val oneRepMaxDeadlift: BigDecimal,

    @Column(name = "jump_height", nullable = false)
    val jumpHeight: BigDecimal,

    @Column(name = "average_run_per_kilometer", nullable = false)
    val averageRunPerKilometer: BigDecimal,

    @Column(name = "shoulder_flexibility", nullable = false)
    val shoulderFlexibility: Int,

    @Column(name = "hip_flexibility", nullable = false)
    val hipFlexibility: Int,

    @Column(name = "balance_time", nullable = false)
    val balanceTime: BigDecimal,

    @Column(name = "reaction_time", nullable = false)
    val reactionTime: BigDecimal,

    @Column(name = "core_stability_score", nullable = false)
    val coreStabilityScore: Int,

    @Column(name = "hemoglobin", nullable = false)
    val hemoglobin: BigDecimal,

    @Column(name = "glucose", nullable = false)
    val glucose: BigDecimal,

    @Column(name = "creatinine", nullable = false)
    val creatinine: BigDecimal,

    @Column(name = "vitamin_d", nullable = false)
    val vitaminD: BigDecimal,

    @Column(name = "iron", nullable = false)
    val iron: BigDecimal,

    @Column(name = "testosterone", nullable = false)
    val testosterone: BigDecimal,

    @Column(name = "cortisol", nullable = false)
    val cortisol: BigDecimal
)