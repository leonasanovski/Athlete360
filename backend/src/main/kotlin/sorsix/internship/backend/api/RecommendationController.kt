package sorsix.internship.backend.api

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.internship.backend.dto.RecommendationCreateRequest
import sorsix.internship.backend.service.RecommendationService

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins = ["http://localhost:4200"])
class RecommendationController(val recommendationService: RecommendationService) {
    @PostMapping
    fun createRecommendation(@Valid @RequestBody body: RecommendationCreateRequest) : ResponseEntity<Long> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(recommendationService.create(body))
    }
}