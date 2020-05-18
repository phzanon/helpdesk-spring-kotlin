package com.pedrozanon.helpdesk.security.jwt

import com.pedrozanon.helpdesk.domain.User
import com.pedrozanon.helpdesk.domain.enum.ProfileEnum
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
class JwtUserFactory {

    fun create(user: User) : JwtUser {
        return JwtUser(
                user.id,
                user.email,
                user.password,
                mapToGrantedAuthorities(user.profileEnum).toMutableList()
        )
    }

    fun mapToGrantedAuthorities(profileEnum: ProfileEnum) : List<GrantedAuthority> {
        val authorities = ArrayList<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(profileEnum.toString()))
        return authorities
    }
}