package com.pedrozanon.helpdesk.service

import com.pedrozanon.helpdesk.security.jwt.JwtUserFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val userService: UserService,
    private val jwtUserFactory: JwtUserFactory
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userService.findByEmail(email)

        if(user == null)
            throw UsernameNotFoundException(String.format("No user found with username {}", email))
        else {
            return jwtUserFactory.create(user)
        }
    }
}