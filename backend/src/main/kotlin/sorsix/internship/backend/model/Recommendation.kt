package sorsix.internship.backend.model

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import sorsix.internship.backend.model.enum.RecommendationType
import sorsix.internship.backend.model.enum.RestrictionLevel

@Entity
@Table(name = "recommendation")
data class Recommendation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    val recommendationId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    val report: AthleteReport,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    val type: RecommendationType,

    @Enumerated(EnumType.STRING)
    @Column(name = "restriction_level", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    val restrictionLevel: RestrictionLevel = RestrictionLevel.NORMAL,

    @Column(nullable = false, length = 70)
    val label: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val description: String,

    @Column(name = "cost_per_month", nullable = false)
    val costPerMonth: Int,

    @Column(name = "duration_weeks", nullable = false)
    val durationWeeks: Int,

    @Column(name = "frequency_per_day", nullable = false)
    val frequencyPerDay: Int = 0,

    @Column(name = "target_goal", nullable = false, columnDefinition = "TEXT")
    val targetGoal: String,

    @Column(name = "effectiveness_rating")
    val effectivenessRating: Int,

    @Column(name = "doctor_personalized_notes", nullable = false, columnDefinition = "TEXT")
    val doctorPersonalizedNotes: String
)