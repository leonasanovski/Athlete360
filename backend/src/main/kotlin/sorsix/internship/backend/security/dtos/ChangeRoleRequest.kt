package sorsix.internship.backend.security.dtos

import sorsix.internship.backend.security.model.UserRole

data class ChangeRoleRequest(
    val embg: String,
    val role: UserRole
)
