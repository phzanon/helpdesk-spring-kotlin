package com.pedrozanon.helpdesk.security.controller

import com.pedrozanon.helpdesk.security.jwt.JwtAuthenticationRequest
import com.pedrozanon.helpdesk.security.jwt.JwtTokenUtil
import com.pedrozanon.helpdesk.security.model.CurrentUser
import com.pedrozanon.helpdesk.service.JwtUserDetailsService
import com.pedrozanon.helpdesk.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@CrossOrigin(origins = arrayOf("*") as Array<String>)
class AuthenticationController (
    private val jwtTokenUtil: JwtTokenUtil,
    private val jwtUserDetailsService: JwtUserDetailsService,
    private val userService: UserService
){

    lateinit var authenticationManager: AuthenticationManager

    @PostMapping("/api/auth")
    fun createAuthenticationToken(@RequestBody jwtAuthenticationRequest: JwtAuthenticationRequest) : ResponseEntity<CurrentUser>{
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(jwtAuthenticationRequest.email,
                                                  jwtAuthenticationRequest.password)
        val authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken)
        SecurityContextHolder.getContext().authentication = authentication
        val userDetails = jwtUserDetailsService.loadUserByUsername(jwtAuthenticationRequest.email)
        val token = jwtTokenUtil.generateToken(userDetails)
        val user = userService.findByEmail(jwtAuthenticationRequest.email)
        user.password = null!!
        return ResponseEntity.ok(CurrentUser(token, user))
    }

    @PostMapping("/api/refresh")
    fun refreshAndGetAuthenticationToken(httpServletRequest: HttpServletRequest) : ResponseEntity<CurrentUser>{
        val token = httpServletRequest.getHeader("Authorization")
        val username = jwtTokenUtil.getUsernameFromToken(token)
        val user = userService.findByEmail(username!!)

        return if(jwtTokenUtil.canTokenBeRefreshed(token)) {
            val refreshToken = jwtTokenUtil.refreshToken(token)
            ResponseEntity.ok(CurrentUser(refreshToken,user))
        } else {
            ResponseEntity.badRequest().body(null)
        }
    }
}