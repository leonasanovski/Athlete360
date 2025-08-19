package sorsix.internship.backend.security.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Collections

class UserPrincipal(val appUser: AppUser) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return Collections.singleton(SimpleGrantedAuthority("PENDING"))
    }

    override fun getPassword(): String? {
        return appUser.passwordHash
    }

    override fun getUsername(): String? {
        return appUser.embg
    }

}