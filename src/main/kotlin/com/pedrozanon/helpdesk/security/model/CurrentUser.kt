package com.pedrozanon.helpdesk.security.model

import com.pedrozanon.helpdesk.domain.User

class CurrentUser(
    private val token : String,
    private val user: User
) {


}