package sorsix.internship.backend.security.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.internship.backend.security.model.AppUser
import sorsix.internship.backend.security.model.UserRole

@Repository
interface AppUserRepository : JpaRepository<AppUser, Long> {
    fun findByEmbg(embg: String): AppUser?
    fun findAllByRole(role: UserRole, pageable: Pageable): Page<AppUser>
    fun findAllByRoleAndEmbgContainingIgnoreCase(role: UserRole, embg: String, pageable: Pageable): Page<AppUser>
}