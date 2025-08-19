package sorsix.internship.backend.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import sorsix.internship.backend.security.model.UserPrincipal
import sorsix.internship.backend.security.repository.AppUserRepository

@Service
class UserDetailsServiceImpl(val userRepository: AppUserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails? {
        if (username.isNullOrBlank()) {
            throw UsernameNotFoundException("Username cannot be null or empty")
        }

        return try {
            val user = userRepository.findByEmbg(username)
                ?: throw UsernameNotFoundException("User with EMBG $username not found")

            UserPrincipal(user)
        } catch (ex: UsernameNotFoundException) {
            throw ex
        } catch (ex: Exception) {
            throw UsernameNotFoundException("Error loading user: ${ex.message}")
        }
    }
}