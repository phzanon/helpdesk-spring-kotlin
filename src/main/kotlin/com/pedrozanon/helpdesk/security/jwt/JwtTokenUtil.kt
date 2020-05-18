package com.pedrozanon.helpdesk.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

@Component
class JwtTokenUtil : Serializable {

    var CLAIM_KEY_USERNAME : String = ""
    var CLAIM_KEY_CREATED : String = ""
    var CLAIM_KEY_EXPIRATED : String = ""

    @Value("\${jwt.secret}")
    lateinit var secret : String

    @Value("\${jwt.expiration}")
    lateinit var expiration : Number

    fun getUsernameFromToken(token : String) : String? {
        val claims = getClaimsFromToken(token)
        if(claims != null) {
            return claims.subject
        }
        return null
    }

    fun getExpirationDateFromToken(token: String) : Date {
        val claims = getClaimsFromToken(token)
        if (claims != null) {
            return claims.expiration
        }
        return null!!
    }

    fun getClaimsFromToken(token: String) : Claims {
        var claims : Claims

        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .body
        }
        catch (exception : Exception) {
            claims = null!!
        }

        return claims
    }

    fun isTokenExpirated(token: String) : Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun generateToken(userDetails : UserDetails) : String {
        var claims = HashMap<String, Any>()

        claims.put(CLAIM_KEY_USERNAME, userDetails.username)
        val createdDate = Date()
        claims.put(CLAIM_KEY_CREATED, createdDate)

        return doGenerateToken(claims)
    }

    fun doGenerateToken(claims: Map<String, Any>) : String {
        val createdDate= claims.get(CLAIM_KEY_CREATED) as Date
        val expirationDate = Date(createdDate.time + expiration.toLong() * 1000)
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    fun canTokenBeRefreshed(token: String) : Boolean {
        return (!isTokenExpirated(token))
    }

    fun refreshToken(token: String) : String {
        var refreshedToken : String
        try {
            val claims = getClaimsFromToken(token)
            claims.put(CLAIM_KEY_CREATED, Date())
            refreshedToken = doGenerateToken(claims)
        }
        catch (exception : Exception) {
            refreshedToken = null!!
        }

        return refreshedToken
    }

    fun validateToken(token: String, userDetails: UserDetails) : Boolean {
        val user = userDetails as JwtUser
        val username = getUsernameFromToken(token)
        return username.equals(user.username) && !isTokenExpirated(token)
    }
}