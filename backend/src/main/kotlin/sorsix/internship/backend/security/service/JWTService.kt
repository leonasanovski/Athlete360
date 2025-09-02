package sorsix.internship.backend.security.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import sorsix.internship.backend.repository.DoctorRepository
import sorsix.internship.backend.repository.PatientRepository
import sorsix.internship.backend.security.model.UserRole
import sorsix.internship.backend.security.repository.AppUserRepository
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey


@Service
class JWTService(
    @Value("\${jwt.secret}") private val secretKey: String,
    private val userRepository: AppUserRepository,
    private val doctorRepository: DoctorRepository,
    private val patientRepository: PatientRepository
) {
    fun generateToken(username: String): String {
        val user = userRepository.findByEmbg(username)
            ?: throw UsernameNotFoundException("User with EMBG $username not found")
        val personId: Long? = when (user.role) {
            UserRole.DOCTOR -> {
                doctorRepository.findByUserEmbg(username)?.doctorId
            }

            UserRole.PATIENT -> {
                patientRepository.findByUserEmbg(username)?.patientId
            }

            else -> {
                null
            }
        }
        val claims = mutableMapOf<String, Any?>(
            "role" to user.role,
            "firstName" to user.firstName,
            "lastName" to user.lastName,
            "personId" to personId,
            "userId" to user.userId
        )
        return Jwts.builder()
            .claims()
            .add(claims)
            .subject(username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + java.time.Duration.ofDays(1).toMillis())) // 1 day
            .and()
            .signWith(getKey())
            .compact()
    }

    private fun getKey(): SecretKey =
        Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

    private fun extractAllClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .payload

    fun extractEmbg(token: String): String =
        extractAllClaims(token).subject

    private fun isTokenExpired(token: String): Boolean =
        extractAllClaims(token).expiration.before(Date())

    fun validateToken(token: String, userDetails: UserDetails): Boolean =
        extractEmbg(token) == userDetails.username && !isTokenExpired(token)
}