package sorsix.internship.backend.model

import jakarta.persistence.*

@Entity
@Table(name = "summary")
data class Summary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "summary_id")
    var summaryId: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false, unique = true)
    var athleteReport: AthleteReport,

    @Column(name = "summarized_content", nullable = false)
    var summarizedContent: String
)
