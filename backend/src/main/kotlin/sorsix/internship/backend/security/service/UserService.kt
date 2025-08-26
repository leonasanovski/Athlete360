package sorsix.internship.backend.security.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import sorsix.internship.backend.security.dtos.ChangeRoleRequest
import sorsix.internship.backend.security.dtos.LoginRequest
import sorsix.internship.backend.security.dtos.RegisterRequest
import sorsix.internship.backend.security.model.AppUser
import sorsix.internship.backend.security.model.UserRole
import sorsix.internship.backend.security.repository.AppUserRepository
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepository: AppUserRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JWTService
) {

    private val encoder = BCryptPasswordEncoder(12)

    fun register(request: RegisterRequest): AppUser {
        if (request.password != request.confirmPassword) {
            throw RuntimeException("Passwords don't match!")
        }

        if (userRepository.findByEmbg(request.embg) != null) {
            throw RuntimeException("User already exists!")
        }

        val user = AppUser(
            embg = request.embg,
            email = request.email,
            firstName = request.firstName,
            lastName = request.lastName,
            passwordHash = encoder.encode(request.password),
            createdAt = LocalDateTime.now(),//?
            role = UserRole.PENDING//?
        )

        return userRepository.save(user)
    }

    fun login(request: LoginRequest): String {
        val auth: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.embg, request.password)
        )

        return if (auth.isAuthenticated) {
            jwtService.generateToken(request.embg)
        } else {
            "Something went wrong"
        }
    }

    fun changeAuthority(request: ChangeRoleRequest): AppUser {
        val user = userRepository.findByEmbg(request.embg)
            ?: throw UsernameNotFoundException("User Not Found")

        user.role = request.role
        return userRepository.save(user)
    }

    fun getCurrentUser(): AppUser {
        val authentication = SecurityContextHolder.getContext().authentication
        val username = authentication.name
        return userRepository.findByEmbg(username)
            ?: throw UsernameNotFoundException("User Not Found")
    }
}