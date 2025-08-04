package sorsix.internship.backend.model

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.Type
import org.hibernate.type.SqlTypes
import sorsix.internship.backend.model.enum.MoodEmotion
import sorsix.internship.backend.model.enum.MoodProgress
import java.time.LocalDateTime

@Entity
@Table(name = "mood")
data class Mood(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mood_id")
    val moodId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    val patient: Patient,

    @Enumerated(EnumType.STRING)
    @Column(name = "mood_progress", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    val moodProgress: MoodProgress,//rezultat od celata presmetka

    @Enumerated(EnumType.STRING)
    @Column(name = "mood_emotion", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    val moodEmotion: MoodEmotion,//rezultat od celata presmetka

    @Column(name = "hours_slept_average", nullable = false)
    val hoursSleptAverage: Int,

    @Column(name = "mood_description", nullable = false)
    val moodDescription: String,//toa shto go kuca pacientot

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)

