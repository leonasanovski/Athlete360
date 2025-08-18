package sorsix.internship.backend.api

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sorsix.internship.backend.dto.SummaryCreateRequest
import sorsix.internship.backend.service.SummaryService

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping(value = ["/api/summary"])
class SummaryController(val summaryService: SummaryService) {

    @PostMapping
    fun create(@Valid @RequestBody request: SummaryCreateRequest): ResponseEntity<Long> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(summaryService.create(request))
    }

    @GetMapping("/ai/{reportId}")
    fun getWithAI(@PathVariable reportId: Long): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(summaryService.getSummaryAI(reportId))
    }

    @PatchMapping("/{reportId}")
    fun patchSummary(
        @PathVariable reportId: Long,
        @RequestBody request: SummaryCreateRequest
    ): ResponseEntity<Long> {
        val updatedId = summaryService.update(reportId, request)
        return ResponseEntity.ok(updatedId)
    }
}