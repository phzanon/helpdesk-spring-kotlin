package com.pedrozanon.helpdesk.domain

import com.pedrozanon.helpdesk.domain.enum.ProfileEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Document
data class User(
    @Id
    val id: String,

    @Indexed(unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email")
    val email: String,

    @NotBlank(message = "Password is required")
    @Size(min = 6)
    var password: String,

    val profileEnum: ProfileEnum
)