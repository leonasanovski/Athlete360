package sorsix.internship.backend.security.config

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import sorsix.internship.backend.security.service.JWTService
import java.io.IOException

@Component
class JwtFilter(
    private val jwtService: JWTService,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        var token: String? = null
        var embg: String? = null
        if (!authHeader.isNullOrBlank() && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7)
            println("Token: $token")
            embg = jwtService.extractEmbg(token)
            println("Embg: $embg")
        }

        if (!embg.isNullOrBlank() && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(embg)
            println("[JWT] userDetails.username=${userDetails.username}")
            println("[JWT] subjectMatches=${embg == userDetails.username}")
            if (token != null && jwtService.validateToken(token, userDetails)) {
                println("[JWT] token is valid")
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                ).apply {
                    details = WebAuthenticationDetailsSource().buildDetails(request)
                }
                SecurityContextHolder.getContext().authentication = authToken
                println("[JWT] authentication set for ${userDetails.username}")
            }
        }
        println("[JWT] auth before chain = ${SecurityContextHolder.getContext().authentication}")
        filterChain.doFilter(request, response)
        println("[JWT] auth after chain = ${SecurityContextHolder.getContext().authentication}")
    }
}
