package sorsix.internship.backend.security.dtos

data class RegisterRequest(
    val embg: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val confirmPassword: String
)
