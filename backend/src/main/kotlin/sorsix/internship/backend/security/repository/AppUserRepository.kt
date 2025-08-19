package sorsix.internship.backend.security.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sorsix.internship.backend.security.model.AppUser

@Repository
interface AppUserRepository : JpaRepository<AppUser, Long> {
    fun findByEmbg(embg: String): AppUser?
}