package sorsix.internship.backend.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "mood")
data class Mood(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mood_id")
    val moodId: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    val patient: Patient,

    @Column(name = "mood_level", nullable = false)
    val moodLevel: String,

    @Column(name = "mood_description", nullable = false)
    val moodDescription: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime
)
