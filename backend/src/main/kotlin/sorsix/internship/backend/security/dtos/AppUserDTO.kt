package sorsix.internship.backend.security.dtos

import sorsix.internship.backend.security.model.AppUser
import sorsix.internship.backend.security.model.UserRole
import java.time.LocalDateTime


data class AppUserDTO(
    val userId: Long?,
    val firstName: String,
    val lastName: String,
    val embg: String,
    val role: UserRole,
    val email: String?,
    val createdAt: LocalDateTime?
) {
    companion object {
        fun fromEntity(appUser: AppUser): AppUserDTO =
            AppUserDTO(
                userId = appUser.userId,
                firstName = appUser.firstName,
                lastName = appUser.lastName,
                embg = appUser.embg,
                role = appUser.role,
                email = appUser.email,
                createdAt = appUser.createdAt
            )
    }
}