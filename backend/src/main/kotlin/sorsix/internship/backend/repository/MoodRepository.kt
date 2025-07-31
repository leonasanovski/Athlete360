package sorsix.internship.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.internship.backend.model.Mood

@Repository
interface MoodRepository : JpaRepository<Mood, Long> {
}