package com.pedrozanon.helpdesk.security.jwt

import java.io.Serializable

class JwtAuthenticationRequest(
    val email : String,
    val password : String
) : Serializable