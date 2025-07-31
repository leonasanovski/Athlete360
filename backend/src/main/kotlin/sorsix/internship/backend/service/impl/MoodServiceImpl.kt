package sorsix.internship.backend.service.impl

import org.springframework.stereotype.Service
import sorsix.internship.backend.repository.MoodRepository
import sorsix.internship.backend.service.MoodService

@Service
class MoodServiceImpl(private val moodRepository: MoodRepository) : MoodService {
}