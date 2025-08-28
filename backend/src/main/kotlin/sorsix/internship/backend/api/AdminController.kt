package sorsix.internship.backend.api

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import sorsix.internship.backend.security.dtos.AppUserDTO
import sorsix.internship.backend.security.model.AppUser
import sorsix.internship.backend.security.model.UserRole
import sorsix.internship.backend.security.repository.AppUserRepository


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = ["http://localhost:4200"])
@PreAuthorize("hasRole('ADMIN')")
class AdminController(
    private val userRepository: AppUserRepository
) {
    @GetMapping("/get-all-pending")
    fun getAllPending(
        @PageableDefault(size = 20, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable,
        @RequestParam("embg", required = false) embg: String?
    ) : ResponseEntity<Page<AppUserDTO>> {
        val usersPage: Page<AppUser> = if (embg.isNullOrBlank()) {
            userRepository.findAllByRole(UserRole.PENDING, pageable)
        } else {
            userRepository.findAllByRoleAndEmbgContainingIgnoreCase(UserRole.PENDING, embg, pageable)
        }
        return ResponseEntity.ok(usersPage.map { AppUserDTO.fromEntity(it) })
    }


    @PatchMapping("/users/{id}/role")
    fun updateUserRole(
        @PathVariable id: Long,
        @RequestParam("role") role: UserRole
    ): ResponseEntity<AppUserDTO> {
        val user = userRepository.findById(id).orElse(null)
            ?: return ResponseEntity.notFound().build()
        user.role = role
        val updated = userRepository.save(user)
        return ResponseEntity.ok(AppUserDTO.fromEntity(updated))
    }


    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        if (!userRepository.existsById(id)) return ResponseEntity.notFound().build()
        userRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}