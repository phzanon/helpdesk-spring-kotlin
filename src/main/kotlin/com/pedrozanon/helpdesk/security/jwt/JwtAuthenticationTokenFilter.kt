package com.pedrozanon.helpdesk.security.jwt

import com.pedrozanon.helpdesk.security.jwt.JwtTokenUtil
import com.pedrozanon.helpdesk.service.JwtUserDetailsService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class JwtAuthenticationTokenFilter: OncePerRequestFilter() {

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    lateinit var userDetailsService: JwtUserDetailsService

    companion object {
        private val logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter::class.java)
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authToken = request.getHeader("Authorization")
        val username = jwtTokenUtil.getUsernameFromToken(authToken)

        if(username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)
            if(jwtTokenUtil.validateToken(authToken, userDetails)) {
                var authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                logger.info("Authenticated user " + username + "setting security context")
                SecurityContextHolder.getContext().authentication = authentication
            }
            filterChain.doFilter(request, response)
        }

        //filterChain.doFilter(request, response)
    }
}